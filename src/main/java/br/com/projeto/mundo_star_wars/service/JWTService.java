package br.com.projeto.mundo_star_wars.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTService {

  @Value("${jwt.secret}")
  private String secretKey;
  private static final long EXPIRATION_TIME = 12 * 60 * 60 * 1000; // 12 horas

  public String gerarToken(String email) {

    return Jwts.builder()
        .setSubject(email)
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(SignatureAlgorithm.HS512, secretKey)
        .compact();
  }

}
