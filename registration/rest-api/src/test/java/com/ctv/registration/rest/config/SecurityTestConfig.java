package com.ctv.registration.rest.config;

import com.ctv.security.config.client.CtvUserDetailsBuilder;
import com.ctv.security.config.client.SecurityConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author Dmitry Kovalchuk
 */
@Configuration
public class SecurityTestConfig extends SecurityConfig {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    static class CustomUserDetailsService implements UserDetailsService {

        @Override
        public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
            return CtvUserDetailsBuilder.get()
                    .withEmail("email@admin.com")
                    .withId(1)
                    .withPassword("password")
                    .withSite("http://user.me")
                    .withType("organizer")
                    .withUsername("username")
                    .withAuthorities(AuthorityUtils.createAuthorityList("ROLE_USER"))
                    .build();
        }
    }
}
