package com.ctv.security.config.client;

import org.springframework.security.core.userdetails.UserDetails;

public interface TokenUserDetailsService {

    UserDetails loadUserByToken(String token);

}
