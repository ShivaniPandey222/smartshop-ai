package com.smartshop.gateway.security;

import com.smartshop.gateway.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import java.nio.charset.StandardCharsets;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationGatewayFilterFactory extends AbstractGatewayFilterFactory<JwtAuthenticationGatewayFilterFactory.Config> {

  private final JwtService jwtService;

  public JwtAuthenticationGatewayFilterFactory(JwtService jwtService) {
    super(Config.class);
    this.jwtService = jwtService;
  }


  private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
    ServerHttpResponse response = exchange.getResponse();
    response.setStatusCode(HttpStatus.UNAUTHORIZED);
    response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
    byte[] byteStream = message.getBytes(StandardCharsets.UTF_8);
    DataBuffer df = response.bufferFactory().wrap(byteStream);
    return response.writeWith(Mono.just(df));
  }

  private String extractToken(ServerHttpRequest request){
    String auth = request.getHeaders().getFirst("Authorization");
    if(!StringUtils.isEmpty(auth) && auth.startsWith("Bearer ")){
      return auth.substring(7);
    }
    return null;
  }

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      String token = extractToken(exchange.getRequest());
      try {
        if (token != null && jwtService.validateToken(token)) {
          String username = jwtService.extractUsername(token);
          ServerHttpRequest mutatedRequest = exchange.getRequest().mutate().header("X-User-Id", username).build();
          return chain.filter(exchange.mutate().request(mutatedRequest).build());
        } else {
          return unauthorized(exchange, "{\"error\":\"Missing or invalid JWT\"}");
        }
      } catch (ExpiredJwtException e) {
        return unauthorized(exchange, "{\"error\":\"JWT is expired\"}");
      } catch (JwtException e) {
        return unauthorized(exchange, "{\"error\":\"Invalid JWT\"}");
      }
    };
  }

  public static class Config {

  }
}
