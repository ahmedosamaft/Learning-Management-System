package fci.swe.advanced_software.utils;

import fci.swe.advanced_software.models.users.AbstractUser;
import fci.swe.advanced_software.utils.adapters.UserDetailsAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthUtils {
    public AbstractUser getCurrentUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if(authentication == null) {
            return null;
        }
        UserDetailsAdapter userDetailsAdapter = (UserDetailsAdapter) authentication.getPrincipal();
        return userDetailsAdapter.user();
    }
}
