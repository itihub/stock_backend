package xyz.itihub.mvc.security.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import xyz.itihub.mvc.security.token.CustomAuthenticationToken;

import java.util.HashSet;
import java.util.Set;

public class CustomAdminAuthenticationProvider implements AuthenticationProvider {


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        if ("admin".equalsIgnoreCase(name) && "chageit".equalsIgnoreCase(password)) {
            Set<SimpleGrantedAuthority> authoritys = new HashSet<>();
            authoritys.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            return new CustomAuthenticationToken(name, password, authoritys);
        } else {
            throw new BadCredentialsException("Not an admin user");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(CustomAuthenticationToken.class);
    }
}
