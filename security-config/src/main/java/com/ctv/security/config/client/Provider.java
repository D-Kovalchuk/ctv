package com.ctv.security.config.client;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Dmitry Kovalchuk
 */
public class Provider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    public Provider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Determine username
        String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
        CtvUserDetails ctvUserDetails = (CtvUserDetails) userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(ctvUserDetails, ctvUserDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
