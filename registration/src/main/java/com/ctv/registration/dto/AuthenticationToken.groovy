package com.ctv.registration.dto

import org.springframework.security.core.GrantedAuthority

class AuthenticationToken {

    String userName
    Collection<? extends GrantedAuthority> authorities
    String sessionId

    AuthenticationToken(String userName, Collection<? extends GrantedAuthority> authorities, String sessionId) {
        this.userName = userName
        this.authorities = authorities
        this.sessionId = sessionId
    }
}
