package fci.swe.advanced_software.services.auth;

import fci.swe.advanced_software.models.users.AbstractUser;
import fci.swe.advanced_software.repositories.users.AbstractUserRepository;
import fci.swe.advanced_software.utils.adapters.UserDetailsAdapter;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class userDetailsServiceImpl implements UserDetailsService {
    private final AbstractUserRepository<AbstractUser> userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AbstractUser user =  userRepository
                                            .findByEmail(username)
                                            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new UserDetailsAdapter(user);
    }
}