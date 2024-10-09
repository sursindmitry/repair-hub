package com.sursindmitry.repairhub.config.security.jwt;


import com.sursindmitry.repairhub.config.security.service.CustomUserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAuthProvider {

  @Value("${spring.application.expiration.token.jwt-token}")
  private Long expirationJwt;

  @Value("${spring.application.jwt-secret}")
  private String jwtSecret;

  private final CustomUserDetailService customUserDetailService;

  @PostConstruct
  protected void init() {
    jwtSecret = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
  }


  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(generteSecretKey(), token);
    return claimsResolver.apply(claims);
  }

  private Claims getAllClaimsFromToken(SecretKey key, String token) {
    JwtParser parser = Jwts.parser().verifyWith(key).build();
    return parser.parseSignedClaims(token).getPayload();
  }

  public String generateToken(String login, Collection<? extends GrantedAuthority> authorities) {
    Map<String, Object> claims = new HashMap<>();

    return Jwts.builder().claims(claims).subject(login)
        .claim("authorities", authorities)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + expirationJwt * 60 * 60 * 1000))
        .signWith(generteSecretKey()).compact();
  }

  private SecretKey generteSecretKey() {
    return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
  }

  public Authentication validateToken(String token) {
    String username = getClaimFromToken(token, Claims::getSubject);

    UserDetails user = customUserDetailService.loadUserByUsername(username);

    return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
  }
}
