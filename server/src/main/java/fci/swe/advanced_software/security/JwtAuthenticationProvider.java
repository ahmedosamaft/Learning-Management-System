package fci.swe.advanced_software.security;

import fci.swe.advanced_software.services.auth.JwtService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Override
    public Authentication authenticate(Authentication authentication) {
        JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication;
        final String jwt = jwtAuthentication.getCredentials().toString();
        final String id = jwtService.extractUsername(jwt);
        UserDetails userDetails = userDetailsService.loadUserByUsername(id);
        
        if (!jwtService.isTokenValid(jwt, userDetails)) {
            return null;
        }

        return JwtAuthentication.authenticated(userDetails);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.isAssignableFrom(authentication);
    }
}
