package com.ctv.registration.mvc.adapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
* @author Dmitry Kovalchuk
*/
public class AuthenticationToken {

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("authorities")
    private List<String> authorities;

    @JsonProperty("sessionId")
    private String sessionId;

    public AuthenticationToken(String userName, List<String> authorities, String sessionId) {
        this.userName = userName;
        this.authorities = authorities;
        this.sessionId = sessionId;
    }

    public String getUserName() {
        return userName;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public String getSessionId() {
        return sessionId;
    }
}
