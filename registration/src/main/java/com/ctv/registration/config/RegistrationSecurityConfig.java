package com.ctv.registration.config;

import com.ctv.security.config.client.CtvUserDetailsService;
import com.ctv.security.config.client.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

import static com.ctv.registration.web.rest.Endpoint.TOKEN_PATH;
import static org.springframework.http.HttpMethod.DELETE;

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
                .usersByUsernameQuery("select * from users where username = ?")
                .passwordEncoder(new BCryptPasswordEncoder())
                .dataSource(dataSource);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.authorizeRequests()
                .antMatchers(DELETE, TOKEN_PATH).authenticated()
                .anyRequest().anonymous();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        CtvUserDetailsService detailsService = new CtvUserDetailsService();
        detailsService.setDataSource(dataSource);
        return detailsService;
    }

}
