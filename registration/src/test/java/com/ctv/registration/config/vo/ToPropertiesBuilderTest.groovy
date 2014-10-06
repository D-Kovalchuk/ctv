package com.ctv.registration.config.vo

import org.springframework.beans.factory.annotation.Value
import spock.lang.Specification

/**
 * @author Timur Yarosh
 */
class ToPropertiesBuilderTest extends Specification {

    def "should create Properties instance with one property where key = apples and value = #value"() {
        given: "a new PropertyHolder created"
        def propertyHolder = new PropertyHolder(10)

        expect: "Properties containing one entry with key apples and value 10"
        def properties = new ToPropertiesBuilder(propertyHolder).toProperties()
        properties.containsKey("apples")
        properties.getProperty("apples").equals("10")
        properties.size() == 1
    }

    static class PropertyHolder {

        @Value('${apples}')
        private int property

        PropertyHolder(int property) {
            this.property = property
        };

    }

}
