package com.ctv.registration.persistence.adapter.model;

import javax.persistence.*;

/**
 * @author Timur Yarosh
 */
@Entity
@Table(name = Authority.TABLE_NAME)
public class Authority {

    public static final String TABLE_NAME = "authorities";
    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name = "username", referencedColumnName = "username")
    private String username;
    @Column(name = "authority")
    private Role roles;

    public Role getRoles() {
        return roles;
    }

    public void setRoles(Role roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public enum Role {
        ROLE_WATCHER
    }

}
