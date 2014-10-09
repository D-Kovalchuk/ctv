package com.ctv.security.config.client;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author Dmitry Kovalchuk
 */
public class CtvUserDetails implements UserDetails {

    private Integer id;
    private String email;
    private String type;
    private String site;
    private String username;
    private String password;
    Collection<? extends GrantedAuthority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    CtvUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, Integer id, String email, String type, String site) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.id = id;
        this.email = email;
        this.type = type;
        this.site = site;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }

    public Integer getId() {
        return id;
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

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
