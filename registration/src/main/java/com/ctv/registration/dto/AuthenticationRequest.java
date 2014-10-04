package com.ctv.registration.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
* @author Dmitry Kovalchuk
*/
public class AuthenticationRequest {

    @JsonProperty("userName")
    private String username;

    @JsonProperty("password")
    private String password;

    public AuthenticationRequest() {
    }

    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}

