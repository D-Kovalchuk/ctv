package com.ctv.conference.rest.adapter.dto;

import java.util.List;

/**
 * @author Dmitry Kovalchuk
 */
public class Conference {

    private String description;

    private String name;

    private String logo;

    private List<Meetup> meetups;

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
}
