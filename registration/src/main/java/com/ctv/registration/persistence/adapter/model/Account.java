package com.ctv.registration.persistence.adapter.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Timur Yarosh
 */
@Entity
@Table(name = Account.TABLE_NAME)
public class Account {

    public static final String TABLE_NAME = "users";

    @Id
    private int id;

    @Column(name = "username")
    private String username;

    @NotBlank
    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name = "enabled")
    private Boolean enabled;

    @Email
    private String email;

    private String type;

    @URL
    private String site;

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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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
