package com.ctv.registration.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Timur Yarosh
 */
public class ErrorInfo {

    @JsonProperty("code")
    private int code;
    @JsonProperty("message")
    private String message;

    public ErrorInfo(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
