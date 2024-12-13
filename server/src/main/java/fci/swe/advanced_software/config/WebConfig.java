package fci.swe.advanced_software.config;

import fci.swe.advanced_software.models.users.AbstractUser;
import fci.swe.advanced_software.repositories.users.AbstractUserRepository;
import fci.swe.advanced_software.utils.adapters.UserDetailsAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class WebConfig {
    private final AbstractUserRepository<AbstractUser> userRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return id -> {
            AbstractUser user = userRepository
                    .findById(id)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return new UserDetailsAdapter(user);
        };
    }
}
