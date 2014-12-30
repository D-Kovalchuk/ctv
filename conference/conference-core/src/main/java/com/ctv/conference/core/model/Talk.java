package com.ctv.conference.core.model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Dmitry Kovalchuk
 */
public class Talk {

    private Integer id;

    private String preview;

    private String title;

    private LocalDateTime date;

    private List<Integer> speakers;

    private String language;

    private String hall;

    private String type;

    private Level level;

    private String format;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<Integer> getSpeakers() {
        return speakers;
    }

    public void setSpeakers(List<Integer> speakers) {
        this.speakers = speakers;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
