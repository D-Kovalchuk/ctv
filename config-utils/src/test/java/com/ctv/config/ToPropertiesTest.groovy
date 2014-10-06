package com.ctv.config

import org.springframework.beans.factory.annotation.Value
import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author Dmitry Kovalchuk
 */
class ToPropertiesTest extends Specification {

    @Unroll
    def "should create Properties instance with one property where key = #key and value = #value"() {
        setup: "a new PropertyHolder created"
        def propertyHolder = new PropertyHolder(value as int)
        def properties = propertyHolder.toProperties()

        expect: "Properties containing one entry with key apples and value 10"
        properties.getProperty(key) == value

        where:
        key      | value
        "apples" | "10"
    }

    private class PropertyHolder extends ToProperties {

        @Value('${apples}')
        private int property

        PropertyHolder(int property) {
            this.property = property
        };

    }

}
