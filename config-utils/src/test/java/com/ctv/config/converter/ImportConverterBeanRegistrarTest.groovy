package com.ctv.config.converter

import com.ctv.config.converter.test.data.Bean1ToBean2Converter
import com.ctv.config.converter.test.data1.Bean3ToBean4Converter
import org.springframework.beans.factory.UnsatisfiedDependencyException
import org.springframework.context.ApplicationContextException
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ConversionServiceFactoryBean
import org.springframework.core.convert.ConversionService
import spock.lang.Specification

/**
 * @author Dmitry Kovalchuk
 */
class ImportConverterBeanRegistrarTest extends Specification {

    def "@EnableConverters with no package specified"() {
        when:
        new AnnotationConfigApplicationContext(Config)

        then:
        thrown(ApplicationContextException)
    }

    def "@EnableConverters with package specified"() {
        given:
        def context = new AnnotationConfigApplicationContext(Config1)

        when:
        def converter = context.getBean(Bean1ToBean2Converter)

        then:
        converter != null
    }

    def "@EnableConverters with createConversionService set to false and none of the conversionService was registered"() {
        when:
        new AnnotationConfigApplicationContext(Config2)

        then:
        thrown(UnsatisfiedDependencyException)
    }

    def "@EnableConverters with createConversionService set to false and conversionService was registered"() {
        setup:
        def context = new AnnotationConfigApplicationContext(Config3)
        def conversionService = context.getBean(ConversionService)

        expect:
        conversionService != null
    }

    def "@EnableConverters register conversionService with custom beanName"() {
        setup:
        def context = new AnnotationConfigApplicationContext(Config5)
        def conversionService = context.getBean("customConversionService")

        expect:
        conversionService != null
    }

    def "register two conversionServices: one by @EnableConverters with custom beanName and another"() {
        setup:
        def context = new AnnotationConfigApplicationContext(Config4)
        def fistsConversionService = context.getBean("customConversionService")
        def secondConversionService = context.getBean("conversionService")

        expect:
        fistsConversionService != null
        secondConversionService != null
    }

    def "@EnableConverters with more then one package specified"() {
        setup:
        def context = new AnnotationConfigApplicationContext(Config6)
        def firstConverter = context.getBean(Bean1ToBean2Converter)
        def secondConverter = context.getBean(Bean3ToBean4Converter)

        expect:
        firstConverter != null
        secondConverter != null
    }

    @Configuration
    @EnableConverters([])
    static class Config {}

    @Configuration
    @EnableConverters("com.ctv.config.converter.test.data")
    static class Config1 {}

    @Configuration
    @EnableConverters(value = "com.ctv.config.converter.test.data", createConversionService = false)
    static class Config2 {}

    @Configuration
    @EnableConverters(value = "com.ctv.config.converter.test.data", createConversionService = false)
    static class Config3 {

        @Bean
        public ConversionServiceFactoryBean conversionServiceFactoryBean() {
            new ConversionServiceFactoryBean()
        }

    }

    @Configuration
    @EnableConverters(value = "com.ctv.config.converter.test.data", beanName = "customConversionService")
    static class Config4 {

        @Bean
        public ConversionServiceFactoryBean conversionService() {
            new ConversionServiceFactoryBean()
        }

    }

    @Configuration
    @EnableConverters(value = "com.ctv.config.converter.test.data", beanName = "customConversionService")
    static class Config5 {}

    @Configuration
    @EnableConverters(["com.ctv.config.converter.test.data", "com.ctv.config.converter.test.data1"])
    static class Config6 {}
}
