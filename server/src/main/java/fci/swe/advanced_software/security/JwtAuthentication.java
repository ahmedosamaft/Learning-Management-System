package fci.swe.advanced_software.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtAuthentication extends AbstractAuthenticationToken {

    private final UserDetails authUser;
    private final String jwtToken;

    private JwtAuthentication(
            Collection<? extends GrantedAuthority> authorities,
            UserDetails authUser,
            boolean authenticated,
            String jwtToken) {
        super(authorities);
        super.setAuthenticated(authenticated);
        this.authUser = authUser;
        this.jwtToken = jwtToken;
    }

    public static JwtAuthentication unauthenticated(String jwtToken) {
        return new JwtAuthentication(null, null, false, jwtToken);
    }

    public static JwtAuthentication authenticated(UserDetails authUser) {
        return new JwtAuthentication(authUser.getAuthorities(), authUser, true, null);
    }

    @Override
    public String getCredentials() {
        return jwtToken;
    }

    @Override
    public Object getPrincipal() {
        return authUser;
    }
}