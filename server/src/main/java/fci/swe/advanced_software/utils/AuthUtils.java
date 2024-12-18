package fci.swe.advanced_software.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthUtils {
    public String getCurrentUserId() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if(authentication == null) {
            return null;
        }
        String userId = authentication.getPrincipal().toString();
        return userId;
    }
}
