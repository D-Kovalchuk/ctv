package com.ctv.security.config.client;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author Dmitry Kovalchuk
 */
public class CtvUserDetails extends User {

    private Integer id;
    private String email;
    private String type;
    private String site;

    CtvUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, Integer id, String email, String type, String site) {
        super(username, password, authorities);
        this.id = id;
        this.email = email;
        this.type = type;
        this.site = site;
    }
}
