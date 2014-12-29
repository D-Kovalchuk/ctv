package com.ctv.conference.core.config;

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
        sessionRepositoryFilter.setHttpSessionStrategy(new HeaderHttpSessionStrategy());
        http.addFilterAfter(sessionRepositoryFilter, ChannelProcessingFilter.class)
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .csrf().disable();
    }

}
