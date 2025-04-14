package fci.swe.advanced_software.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


@Component
public class SecurityAuthenticationFilter extends OncePerRequestFilter {

  private final AuthenticationManager authenticationManager;

  public SecurityAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    Authentication unauthenticatedAuthentication =
        SecurityContextHolder.getContext().getAuthentication();

    if (unauthenticatedAuthentication == null || unauthenticatedAuthentication.isAuthenticated()) {

      filterChain.doFilter(request, response);
      return;
    }

    Authentication authenticatedAuthentication =
        authenticationManager.authenticate(unauthenticatedAuthentication);

    if (authenticatedAuthentication != null) {

      SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
      securityContext.setAuthentication(authenticatedAuthentication);
      SecurityContextHolder.setContext(securityContext);
    }

    filterChain.doFilter(request, response);
  }
}