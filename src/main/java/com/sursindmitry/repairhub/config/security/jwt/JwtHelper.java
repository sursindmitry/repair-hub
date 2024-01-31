package com.sursindmitry.repairhub.config.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtHelper {

  @Value("${spring.application.expiration.token.jwt-token}")
  private Long expirationJwt;

  @Value("${spring.application.jwt-secret}")
  private String jwtSecret;

  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

    final Claims claims = getAllClaimsFromToken(key, token);
    return claimsResolver.apply(claims);
  }

  private Claims getAllClaimsFromToken(SecretKey key, String token) {
    return (Claims) Jwts.parser().verifyWith(key).build().parse(token).getPayload();
  }

  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  public String generateToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    return doGenerateToken(claims, userDetails.getUsername());
  }

  private String doGenerateToken(Map<String, Object> claims, String subject) {
    SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

    return Jwts.builder().claims(claims).subject(subject)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + expirationJwt * 60 * 60 * 1000))
        .signWith(key).compact();
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = getUsernameFromToken(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }
}
