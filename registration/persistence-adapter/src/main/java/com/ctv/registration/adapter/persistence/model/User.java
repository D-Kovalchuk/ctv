package com.ctv.registration.adapter.persistence.model;

import org.hibernate.validator.constraints.*;

import javax.persistence.*;
import java.util.List;

import static com.ctv.registration.adapter.persistence.model.ColumnNames.*;
import static com.ctv.registration.adapter.persistence.model.Constraints.*;
import static javax.persistence.GenerationType.SEQUENCE;

/**
 * @author Timur Yarosh
 */
@Entity
@Table(name = USER_TABLE)
public class User {

    @Id
    @Column(name = ID)
    @GeneratedValue(strategy = SEQUENCE)
    private int id;

    @Column(name = USERNAME, unique = true, nullable = false)
    @Length(min = USERNAME_MINIMUM_LENGTH, max = USERNAME_MAXIMUM_LENGTH)
    private String username;

    @NotEmpty
    @Column(name = PASSWORD)
    private String password;

    @Column(name = ENABLED, nullable = false)
    private Boolean enabled = true;

    @Email
    @Column(name = EMAIL, unique = true)
    private String email;

    @NotEmpty
    @Column(name = TYPE)
    private String type;

    @URL
    @Column(name = SITE)
    private String site;

    @OneToMany(targetEntity = Authority.class)
    @JoinColumn(name = USERNAME, referencedColumnName = USERNAME)
    private List<Authority> authorities;

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
