package com.ctv.registration.config;

import com.ctv.security.config.client.CtvUserDetailsService;
import com.ctv.security.config.client.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
public class RegistrationSecurityConfig extends SecurityConfig {

    public static final String QUERY = "select * from users join authorities using (username)";

    @Autowired
    private DataSource dataSource;

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

    @Override
    @Bean
    public UserDetailsService userDetailsServiceBean() throws Exception {
        CtvUserDetailsService detailsService = new CtvUserDetailsService(QUERY);
        detailsService.setDataSource(dataSource);
        return detailsService;
    }

}
