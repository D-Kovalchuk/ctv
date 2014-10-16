package com.ctv.config
import com.ctv.config.property.ToProperties
import org.springframework.beans.factory.annotation.Value
import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author Dmitry Kovalchuk
 */
class ToPropertiesTest extends Specification {

    @Unroll
    def "should create Properties instance with one property where key = #key and value = #value"() {
        given:
        def propertyHolder = new PropertyHolder(value as int)

        when:
        def properties = propertyHolder.toProperties()

        then: "Properties containing one entry with key apples and value 10"
        properties.getProperty(key) == value

        where:
        key      | value
        "apples" | "10"
    }

    def "IntegerPropertyHolder when property is set"() {
        given:
        def integerHolder = new IntegerPropertyHolder(10)

        when:
        def properties = integerHolder.toProperties()

        then:
        properties.getProperty("apples") as Integer == 10
    }

    def "IntegerPropertyHolder when property is not set"() {
        given:
        def integerHolder = new IntegerPropertyHolder(null)

        when:
        def properties = integerHolder.toProperties()

        then:
        properties.isEmpty()
    }

    def "StringPropertyHolder when property exists but value is not set"() {
        given:
        def stringHolder = new StringPropertyHolder("")

        when:
        def properties = stringHolder.toProperties()

        then:
        properties.isEmpty()
    }

    def "StringPropertyHolder when property does not exist"() {
        given:
        def stringHolder = new StringPropertyHolder(null)

        when:
        def properties = stringHolder.toProperties()

        then:
        properties.isEmpty()
    }


    private class StringPropertyHolder extends ToProperties {

        @Value('${apples}')
        private String property

        StringPropertyHolder(String property) {
            this.property = property
        }

    }

    private class IntegerPropertyHolder extends ToProperties {

        @Value('${apples}')
        private Integer property

        IntegerPropertyHolder(Integer property) {
            this.property = property
        }

    }

    private class PropertyHolder extends ToProperties {

        @Value('${apples}')
        private int property

        PropertyHolder(int property) {
            this.property = property
        }
    }

}
