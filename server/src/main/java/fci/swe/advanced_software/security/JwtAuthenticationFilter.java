package fci.swe.advanced_software.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationFilter extends AuthenticationFilter {

    public JwtAuthenticationFilter(
            AuthenticationManager authenticationManager,
            JwtAuthenticationConverter jwtAuthenticationConverter,
            AuthenticationEntryPoint authenticationEntryPoint) {

        super(authenticationManager, jwtAuthenticationConverter);

        setSuccessHandler((_, _, _) -> {});

        setFailureHandler(authenticationEntryPoint::commence);
    }
}
