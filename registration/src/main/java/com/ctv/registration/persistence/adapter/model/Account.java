package com.ctv.registration.persistence.adapter.model;

import org.hibernate.validator.constraints.NotBlank;

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
    @Column(name = "username")
    private String username;
    @NotBlank
    @Column(name = "password")
    private String password;
    @NotNull
    @Column(name = "enabled")
    private Boolean enabled;

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

}
