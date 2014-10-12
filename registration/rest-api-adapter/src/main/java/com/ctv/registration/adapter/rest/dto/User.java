package com.ctv.registration.adapter.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Dmitry Kovalchuk
 */
//todo add validation
public class User {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

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

    public String getEmail() {
        return email;
    }

    public String getType() {
        return type;
    }

    public String getSite() {
        return site;
    }

    public Integer getId() {
        return id;
    }

}
