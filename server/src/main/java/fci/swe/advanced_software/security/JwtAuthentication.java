package fci.swe.advanced_software.security;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public record JwtAuthentication(UserDetails authUser, boolean authenticated, String jwtToken)
        implements Authentication {

    public static JwtAuthentication unauthenticated(String jwtToken) {
        return new JwtAuthentication(null, false, jwtToken);
    }

    public static JwtAuthentication authenticated(UserDetails authUser) {
        return new JwtAuthentication(authUser, true, null);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authUser.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return jwtToken;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return authUser;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getName() {
        return null;
    }
}