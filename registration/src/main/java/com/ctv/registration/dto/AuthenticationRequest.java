package com.ctv.registration.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
* @author Dmitry Kovalchuk
*/
public class AuthenticationRequest {

    private String username;
    private String password;

    @JsonCreator
    public AuthenticationRequest(@JsonProperty("userName") String username,
                                 @JsonProperty("password") String password) {
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

