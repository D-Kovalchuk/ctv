package com.ctv.registration.rest;

import com.ctv.security.config.client.SecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

/**
 * @author Dmitry Kovalchuk
 */
@Configuration
public class SecurityTestConfig extends SecurityConfig {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("username")
                .password("password")
                .roles("USER");
    }
}
