package com.ctv.security.config.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;

@Configuration
public class SecurityInfrastructureConfig {


    @Bean
    public AlwaysReauthenticateBeanPostProcessor reauthenticateBeanPostProcessor() {
        return new AlwaysReauthenticateBeanPostProcessor();
    }

    @Bean
    public TokenUserDetailsService tokenUserDetailsService(RedisTemplate<String, Authentication> redisTemplate) {
        return new TokenUserDetailsServiceImpl(redisTemplate);
    }

    @Bean
    public RedisAuthenticationProvider redisAuthenticationProvider(TokenUserDetailsService userDetailsService) {
        return new RedisAuthenticationProvider(userDetailsService);
    }



}
