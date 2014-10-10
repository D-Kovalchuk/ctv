package com.ctv.registration.config;

import com.ctv.security.config.client.CtvUserDetailsService;
import com.ctv.security.config.client.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

/**
 * @author Dmitry Kovalchuk
 */
@Configuration
@PropertySource("classpath:security-default.properties")
public class RegistrationSecurityConfig extends SecurityConfig {

    @Autowired
    private DataSource dataSource;

    @Value("${security.loadUsersAndAuthoritiesByUsernameQuery}")
    private String query;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public AuthenticationProvider authenticationProvider() throws Exception {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsServiceBean());
        return authenticationProvider;
    }

    @Bean
    public UserDetailsService userDetailsServiceBean() {
        CtvUserDetailsService detailsService = new CtvUserDetailsService(query);
        detailsService.setDataSource(dataSource);
        return detailsService;
    }

}
