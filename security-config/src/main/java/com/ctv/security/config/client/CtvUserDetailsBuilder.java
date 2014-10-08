package com.ctv.security.config.client;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author Timur Yarosh
 */
public class CtvUserDetailsBuilder {

    private String password;
    private String username;
    private Collection<GrantedAuthority> authorities;
    private Integer id;
    private String email;
    private String type;
    private String site;

    private CtvUserDetailsBuilder() {
    }

    public CtvUserDetailsBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public CtvUserDetailsBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public CtvUserDetailsBuilder withAuthorities(Collection<GrantedAuthority> authorities) {
        this.authorities = authorities;
        return this;
    }

    public CtvUserDetailsBuilder withId(Integer id) {
        this.id = id;
        return this;
    }

    public CtvUserDetailsBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public CtvUserDetailsBuilder withType(String type) {
        this.type = type;
        return this;
    }

    public CtvUserDetailsBuilder withSite(String site) {
        this.site = site;
        return this;
    }

    public CtvUserDetails build() {
        return new CtvUserDetails(
                username,
                password,
                authorities,
                id,
                email,
                type,
                site
        );
    }

    public static CtvUserDetailsBuilder get() {
        return new CtvUserDetailsBuilder();
    }
}
