package com.ctv.registration.config;

import com.ctv.security.config.client.SecurityConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

/**
 * @author Dmitry Kovalchuk
 */
@Configuration
public class RegistrationSecurityConfig extends SecurityConfig {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password("pass")
                .roles("USER");
    }

}
