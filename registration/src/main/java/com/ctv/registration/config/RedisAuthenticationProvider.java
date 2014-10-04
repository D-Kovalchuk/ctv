package com.ctv.registration.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Dmitry Kovalchuk
 */
public class RedisAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    //session token will be pasted
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        userDetailsService.loadUserByUsername(authentication.getName());
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
