package com.ctv.conference.bootstrap;

/**
 * @author Dmitry Kovalchuk
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.session.ExpiringSession;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.SessionRepositoryFilter;

import static com.ctv.conference.rest.api.Endpoint.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/**
 * @author Dmitry Kovalchuk
 */
@Configuration
@EnableWebSecurity
@EnableWebMvcSecurity
@EnableRedisHttpSession
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SessionRepositoryFilter<? extends ExpiringSession> sessionRepositoryFilter;

    @Bean
    public JedisConnectionFactory connectionFactory() throws Exception {
        return new JedisConnectionFactory();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        secureEndpoints(http);
        sessionRepositoryFilter.setHttpSessionStrategy(new HeaderHttpSessionStrategy());
        http.addFilterAfter(sessionRepositoryFilter, ChannelProcessingFilter.class)
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .csrf().disable();
    }

    private void secureEndpoints(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                // conference endpoints
                .antMatchers(POST, CONFERENCE).hasRole("USER")
                .antMatchers(PUT, CONFERENCE).hasRole("USER")
                .antMatchers(DELETE, CONFERENCE + BY_ID).hasRole("USER")
                // meetup endpoints
                .antMatchers(POST, MEETUP_BY_CONFERENCE_ID).hasRole("USER")
                .antMatchers(PUT, MEETUP).hasAuthority("USER")
                .antMatchers(PUT, MEETUP_BY_ID).hasRole("USER")
                .antMatchers(DELETE, MEETUP_BY_ID).hasRole("USER")
                // talk endpoint
                .antMatchers(POST, MEETUP_BY_ID_TALK).hasRole("USER")
                .antMatchers(PUT, TALK).hasRole("USER")
                .antMatchers(PUT, TALK_BY_ID).hasRole("USER")
                .antMatchers(DELETE, TALK_BY_ID).hasRole("USER")
                .antMatchers(POST, ASSIGN_SPEAKER_TO_TALK).hasRole("USER");
    }

}
