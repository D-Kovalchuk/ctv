package com.ctv.registration.adapter.rest.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Size;

import static com.ctv.registration.adapter.rest.Constraints.*;
import static com.ctv.registration.adapter.rest.dto.PropertyNames.PASSWORD;
import static com.ctv.registration.adapter.rest.dto.PropertyNames.USERNAME;

/**
* @author Dmitry Kovalchuk
*/
public class AuthenticationRequest {

    @Size(min = USERNAME_MINIMUM_LENGTH, max = USERNAME_MAXIMUM_LENGTH)
    private String username;

    @Size(min = PASSWORD_MINIMUM_LENGTH, max = PASSWORD_MAXIMUM_LENGTH)
    private String password;

    @JsonCreator
    public AuthenticationRequest(@JsonProperty(USERNAME) String username,
                                 @JsonProperty(PASSWORD) String password) {
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

