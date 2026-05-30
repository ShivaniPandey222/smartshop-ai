package com.smartshop.gateway.service;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    public String extractUsername(String token){
        return Jwts.parser().verifyWith(getSigningKey())
            .build().parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean validateToken(String token){
       return Jwts.parser().verifyWith(getSigningKey())
              .build().parseSignedClaims(token).getPayload().getExpiration().after(new Date());
    }

    private SecretKey getSigningKey(){
      byte[] decodeSecretKey = Decoders.BASE64.decode(secret);
      return Keys.hmacShaKeyFor(decodeSecretKey);
    }
}
