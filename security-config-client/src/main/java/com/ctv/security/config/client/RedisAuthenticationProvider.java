package com.ctv.security.config.client;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author Dmitry Kovalchuk
 */
public class RedisAuthenticationProvider implements AuthenticationProvider {

    private TokenUserDetailsService userDetailsService;

    public RedisAuthenticationProvider(TokenUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    //session token will be pasted
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        userDetailsService.loadUserByToken(authentication.getName());
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
