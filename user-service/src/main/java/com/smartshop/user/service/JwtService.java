package com.smartshop.user.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
      @Value("${jwt.secret}")
      private  String secret;

      @Value("${jwt.expiry}")
      private long expiry;

      public String generateToken(UserDetails userDetails){
                return Jwts.builder()
                    .subject(userDetails.getUsername())
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + expiry))
                    .signWith(getSigningKey())
                    .compact();
      }

      public String extractUsername(String token){
              return extractPayload(token)
                   .getSubject();
      }

      public boolean validateToken(String token, UserDetails userDetails){
         return extractUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
      }

      private boolean isTokenExpired(String token){
        return extractPayload(token)
            .getExpiration().before(new Date());
      }

      private Claims extractPayload(String token){
        return Jwts.parser().verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
      }

      private SecretKey getSigningKey(){
          byte[] decodedSecretKey = Decoders.BASE64.decode(secret);
            return Keys.hmacShaKeyFor(decodedSecretKey);
      }
}
