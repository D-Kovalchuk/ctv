package com.ctv.registration.adapter.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
* @author Dmitry Kovalchuk
*/
public class AuthenticationToken {

    @JsonProperty("username")
    private String userName;

    @JsonProperty("authorities")
    private List<String> authorities;

    public AuthenticationToken(String userName, List<String> authorities) {
        this.userName = userName;
        this.authorities = authorities;
    }

    public String getUserName() {
        return userName;
    }

    public List<String> getAuthorities() {
        return authorities;
    }
}
