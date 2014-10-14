package com.ctv.registration.rest

import groovyx.net.http.RESTClient
import org.springframework.beans.factory.annotation.Value
import spock.lang.Specification
/**
 * @author Dmitry Kovalchuk
 */
class AbstractIntegrationSpecification extends Specification {

    @Value('#{systemProperties["jetty.port"]}')
    String jettyPort;

    RESTClient restClient;

    void setup() {
        restClient = new RESTClient("http://localhost:${jettyPort}/")
    }

    String pathTo(String endpoint) {
        "http://localhost:${jettyPort}${endpoint}"
    }

}
