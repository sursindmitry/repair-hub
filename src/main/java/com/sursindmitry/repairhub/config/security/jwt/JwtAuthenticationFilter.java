package com.sursindmitry.repairhub.config.security.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtHelper jwtHelper;

  private final UserDetailsService userDetailsService;


  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {

    String requestHeader = request.getHeader("Authorization");
    String username = null;
    String token = null;
    if (requestHeader != null && requestHeader.startsWith("Bearer")) {
      token = requestHeader.substring(7);
      try {
        username = this.jwtHelper.getUsernameFromToken(token);
      } catch (IllegalArgumentException e) {
        log.error("Illegal Argument while fetching the username !! {}", e.getMessage());
      } catch (ExpiredJwtException e) {
        log.error("Given jwt token is expired {}", e.getMessage());
      } catch (MalformedJwtException e) {
        log.error("Some changed has done in token {}", e.getMessage());
      } catch (Exception e) {
        log.error("Exception {}", e.getMessage());
      }
    } else {
      log.error("Invalid Header Value");
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
      Boolean validateToken = this.jwtHelper.validateToken(token, userDetails);
      if (validateToken) {
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
      } else {
        log.error("Validation fails");
      }
    }

    filterChain.doFilter(request, response);
  }
}
