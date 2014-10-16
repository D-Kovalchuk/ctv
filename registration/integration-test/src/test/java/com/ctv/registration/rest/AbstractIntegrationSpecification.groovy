package com.ctv.registration.rest
import com.ctv.test.EmbeddedRedis
import groovyx.net.http.RESTClient
import org.junit.Rule
import org.springframework.beans.factory.annotation.Value
import spock.lang.Specification
/**
 * @author Dmitry Kovalchuk
 */
class AbstractIntegrationSpecification extends Specification {

    @Value('#{systemProperties["jetty.port"]}')
    String jettyPort;

    RESTClient restClient;

    @Rule
    EmbeddedRedis embeddedRedis = new EmbeddedRedis(6379)

    void setup() {
        restClient = new RESTClient("http://localhost:${jettyPort}/")
    }

    String pathTo(String endpoint) {
        "http://localhost:${jettyPort}${endpoint}"
    }

}
