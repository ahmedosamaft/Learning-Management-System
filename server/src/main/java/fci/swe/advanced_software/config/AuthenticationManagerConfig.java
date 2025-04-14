package fci.swe.advanced_software.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class AuthenticationManagerConfig {
    @Bean
    public AuthenticationManager authenticationManager(
            List<AuthenticationProvider> authenticationProviders) { 
        ProviderManager providerManager = new ProviderManager(authenticationProviders);

        return providerManager;
    }
}
