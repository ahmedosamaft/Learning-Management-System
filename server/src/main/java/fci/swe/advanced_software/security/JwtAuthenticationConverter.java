package fci.swe.advanced_software.security;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtAuthenticationConverter implements AuthenticationConverter {
    @Override
    public Authentication convert(HttpServletRequest request) {
        String authenticationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authenticationHeader == null || authenticationHeader.isEmpty()) {
            return null;
        }

        String jwtToken = stripBearerPrefix(authenticationHeader);
        return JwtAuthentication.unauthenticated(jwtToken);
    }

    String stripBearerPrefix(String token) {
        if (!token.startsWith("Bearer")) {
            throw new BadCredentialsException("Unsupported authentication scheme");
        }

        return token.substring(7);
    }
}
