package com.ctv.registration.config;

import com.ctv.security.config.client.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

/**
 * @author Dmitry Kovalchuk
 */
@Configuration
public class RegistrationSecurityConfig extends SecurityConfig {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSource);
    }

//    @Override
//    public UserDetailsService userDetailsServiceBean() throws Exception {
//        return new CustomUserDetailsService();
//    }

}
