package com.ctv.conference.core.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmitry Kovalchuk
 */
public class Meetup {

    private Integer id;

    private LocalDateTime date;

    private String address;

    private List<Talk> talks;

    private List<Integer> speakers;

    private boolean hidden;

    private boolean deleted;

    public Meetup() {
        speakers = new ArrayList<>();
    }

    public List<Integer> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(List<Integer> speakers) {
        this.speakers = speakers;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Talk> getTalks() {
        return talks;
    }

    public void setTalks(List<Talk> talks) {
        this.talks = talks;
    }
}
