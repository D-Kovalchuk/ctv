package com.ctv.registration.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Dmitry Kovalchuk
 */
public class User {

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("enabled")
    private Boolean enabled;

    @JsonProperty("email")
    private String email;

    @JsonProperty("type")
    private String type;

    @JsonProperty("site")
    private String site;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public String getEmail() {
        return email;
    }

    public String getType() {
        return type;
    }

    public String getSite() {
        return site;
    }
}
