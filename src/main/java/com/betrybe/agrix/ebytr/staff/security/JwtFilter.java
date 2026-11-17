package com.betrybe.agrix.ebytr.staff.security;

import com.betrybe.agrix.ebytr.staff.service.PersonService;
import com.betrybe.agrix.ebytr.staff.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * The type Jwt filter.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

  private final TokenService tokenService;
  private final PersonService personService;

  /**
   * Instantiates a new Jwt filter.
   *
   * @param tokenService  the token service
   * @param personService the person service
   */
  @Autowired
  public JwtFilter(TokenService tokenService, PersonService personService) {
    this.tokenService = tokenService;
    this.personService = personService;
  }

  @Override
  protected void doFilterInternal(
          HttpServletRequest request,
          HttpServletResponse response,
          FilterChain filterChain) throws ServletException, IOException {

    String authorizationHeader = request.getHeader("Authorization");

    if (authorizationHeader == null
            || !authorizationHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    String token = authorizationHeader.substring(7).trim();

    if (token.isEmpty()) {
      response.sendError(
              HttpServletResponse.SC_UNAUTHORIZED,
              "Token não informado"
      );
      return;
    }

    try {
      String subject = tokenService.validateToken(token);

      UserDetails userDetails =
              personService.loadUserByUsername(subject);

      UsernamePasswordAuthenticationToken userAuthentication =
              new UsernamePasswordAuthenticationToken(
                      userDetails,
                      null,
                      userDetails.getAuthorities()
              );

      SecurityContextHolder.getContext()
              .setAuthentication(userAuthentication);

    } catch (com.auth0.jwt.exceptions.JWTVerificationException
             | org.springframework.security.core.userdetails.UsernameNotFoundException ex) {

      SecurityContextHolder.clearContext();

      response.sendError(
              HttpServletResponse.SC_UNAUTHORIZED,
              "Token inválido, expirado ou usuário não encontrado"
      );
      return;
    }

    filterChain.doFilter(request, response);
  }

  private Optional<String> extractToken(HttpServletRequest request) {
    Optional<String> authHeader = Optional.ofNullable(request.getHeader("Authorization"));

    return authHeader.map(s -> s.replace("Bearer ", ""));
  }
}
