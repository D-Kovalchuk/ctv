package com.ctv.conference.core.model;

import java.util.List;
import java.util.Objects;

/**
 * @author Dmitry Kovalchuk
 */
public class ConferenceModel {

    private Integer id;

    private Integer userId;

    private String description;

    private String name;

    private String logo;

    private List<Meetup> meetups;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<Meetup> getMeetups() {
        return meetups;
    }

    public void setMeetups(List<Meetup> meetups) {
        this.meetups = meetups;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConferenceModel that = (ConferenceModel) o;

        return Objects.equals(id, that.id)
                || Objects.equals(description, that.description)
                || Objects.equals(logo, that.logo)
                || Objects.equals(name, that.name)
                || Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, description, name, logo);
    }
}
