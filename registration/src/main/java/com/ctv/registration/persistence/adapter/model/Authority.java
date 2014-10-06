package com.ctv.registration.persistence.adapter.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Timur Yarosh
 */
@Entity
@Table(name = "authorities")
public class Authority {

    @Id
    @GeneratedValue
    private Integer id;
    private Role roles;

    public Role getRoles() {
        return roles;
    }

    public void setRoles(Role roles) {
        this.roles = roles;
    }

    public enum Role {
        ROLE_WATCHER
    }

}
