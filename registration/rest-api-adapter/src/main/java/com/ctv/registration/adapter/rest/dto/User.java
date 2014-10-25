package com.ctv.registration.adapter.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import static com.ctv.registration.adapter.rest.Constraints.*;
import static com.ctv.registration.adapter.rest.dto.PropertyNames.*;
import static com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion.NON_NULL;

/**
 * @author Dmitry Kovalchuk
 */
@JsonSerialize(include= NON_NULL)
public class User {

    @JsonProperty(ID)
    @JsonView(ResponseView.class)
    private Integer id;

    @JsonProperty(USERNAME)
    @JsonView(ResponseView.class)
    @Length(min = USERNAME_MINIMUM_LENGTH, max = USERNAME_MAXIMUM_LENGTH)
    private String username;

    @JsonProperty(PASSWORD)
    @Length(min = PASSWORD_MINIMUM_LENGTH, max = PASSWORD_MAXIMUM_LENGTH)
    private String password;

    @Email
    @JsonProperty(EMAIL)
    @JsonView(ResponseView.class)
    private String email;

    @NotEmpty
    @JsonProperty(TYPE)
    @JsonView(ResponseView.class)
    private String type;

    @URL
    @JsonProperty(SITE)
    @JsonView(ResponseView.class)
    private String site;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

}
