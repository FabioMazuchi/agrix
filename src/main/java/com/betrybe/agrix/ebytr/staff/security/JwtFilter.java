package com.betrybe.agrix.ebytr.staff.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.betrybe.agrix.ebytr.staff.service.PersonService;
import com.betrybe.agrix.ebytr.staff.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * The type Jwt filter.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {
  private static final Logger logger =
          LoggerFactory.getLogger(JwtFilter.class);

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

    Optional<String> token = extractToken(request);

    if (token.isPresent()) {
      try {
        String subject = tokenService.validateToken(token.get());

        UserDetails userDetails =
                personService.loadUserByUsername(subject);

        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

      } catch (JWTVerificationException ex) {

        SecurityContextHolder.clearContext();

        logger.warn(
                "Falha na validação do JWT. Tipo: {}. Mensagem: {}",
                ex.getClass().getSimpleName(),
                ex.getMessage()
        );

        response.sendError(
                HttpServletResponse.SC_UNAUTHORIZED,
                "Token inválido ou expirado"
        );

        return;

      } catch (UsernameNotFoundException ex) {

        SecurityContextHolder.clearContext();

        logger.warn(
                "JWT válido, mas o usuário associado não foi encontrado. Tipo: {}",
                ex.getClass().getSimpleName()
        );

        response.sendError(
                HttpServletResponse.SC_UNAUTHORIZED,
                "Usuário associado ao token não encontrado"
        );

        return;
      }
    }

    filterChain.doFilter(request, response);
  }

  private Optional<String> extractToken(HttpServletRequest request) {
    String authorizationHeader = request.getHeader("Authorization");

    if (authorizationHeader == null
            || !authorizationHeader.startsWith("Bearer ")) {
      return Optional.empty();
    }

    String token = authorizationHeader.substring(7).trim();

    if (token.isEmpty()) {
      return Optional.empty();
    }

    return Optional.of(token);
  }
}
