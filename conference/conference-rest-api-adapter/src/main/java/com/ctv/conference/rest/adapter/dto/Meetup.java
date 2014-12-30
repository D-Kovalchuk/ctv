package com.ctv.conference.rest.adapter.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Dmitry Kovalchuk
 */
public class Meetup {

    private Integer id;

    private LocalDateTime date;

    private String address;

    private List<Talk> talks;

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
