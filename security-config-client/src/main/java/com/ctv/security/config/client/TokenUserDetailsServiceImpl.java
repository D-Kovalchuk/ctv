package com.ctv.security.config.client;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public class TokenUserDetailsServiceImpl implements TokenUserDetailsService {

    private RedisTemplate<String, Authentication> redisTemplate;

    public TokenUserDetailsServiceImpl(RedisTemplate<String, Authentication> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public UserDetails loadUserByToken(String token) {
        return null;
    }

}
