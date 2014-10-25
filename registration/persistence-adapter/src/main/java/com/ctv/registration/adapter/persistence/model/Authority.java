package com.ctv.registration.adapter.persistence.model;


import javax.persistence.*;

import static com.ctv.registration.adapter.persistence.model.ColumnNames.*;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.SEQUENCE;

/**
 * @author Timur Yarosh
 */
@Entity
@Table(name = AUTHORITIES_TABLE)
public class Authority {

    @Id
    @Column(name = ID)
    @GeneratedValue(strategy = SEQUENCE)
    private Integer id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = USERNAME, referencedColumnName = USERNAME)
    private String username;

    @Enumerated(STRING)
    @Column(name = AUTHORITY)
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
