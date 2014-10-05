package com.ctv.registration.config.dto

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Immutable

@Immutable
class AuthenticationToken {
    @JsonProperty("userName")
    private String userName;

    @JsonProperty("authorities")
    private List<String> authorities;

    @JsonProperty("sessionId")
    private String sessionId;
}
