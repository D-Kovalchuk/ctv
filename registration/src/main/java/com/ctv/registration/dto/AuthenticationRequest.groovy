package com.ctv.registration.dto
import com.fasterxml.jackson.annotation.JsonProperty

class AuthenticationRequest {

    @JsonProperty("username")
    String username
    @JsonProperty("password")
    String password

}
