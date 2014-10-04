package com.ctv.registration.dto

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

/**
 * @author Dmitry Kovalchuk
 */
class AuthenticationRequestTest extends Specification {

    def "sdf"() {
        given:
        def mapper = new ObjectMapper()
        def string = mapper.writeValueAsString(new AuthenticationRequest("user", "pass"))

        println(string)
    }
}
