package com.ctv.registration.core.model;

/**
 * @author Dmitry Kovalchuk
 */
public class UserModel {

    private Integer id;

    private String username;

    private String password;

    private String email;

    private String type;

    private String site;

    private Boolean enabled;

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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSite(String site) {
        this.site = site;
    }
}
