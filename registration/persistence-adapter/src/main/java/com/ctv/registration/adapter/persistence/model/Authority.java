package com.ctv.registration.adapter.persistence.model;


import javax.persistence.*;

import static javax.persistence.EnumType.STRING;

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

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "username", referencedColumnName = "username")
    private String username;

    @Column(name = "authority")
    @Enumerated(STRING)
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
        ROLE_WATCHER,
        ROLE_ORGANIZER,
        ROLE_SPEAKER
    }

}
