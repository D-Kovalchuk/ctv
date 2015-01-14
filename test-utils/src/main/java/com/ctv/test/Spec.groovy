package com.ctv.test

import org.mockito.internal.creation.CglibMockMaker
import spock.lang.Specification

import java.lang.reflect.Field

import static org.mockito.Mockito.reset
import static org.springframework.util.ReflectionUtils.getField
/**
 * @author Dmitry Kovalchuk
 */
class Spec extends Specification {

    static CglibMockMaker cglibMockMaker = new CglibMockMaker();

    def cleanup() {
        this.class.getDeclaredFields()
                .findAll {isMock(this, it)}
                .collect {fieldToObject(this, it)}
                .forEach {reset(it)}
    }

    def fieldToObject(final Object testInstance, Field input) {
        return getField(input, testInstance);
    }

    def isMock(final Object testInstance, Field input) {
        input.setAccessible(true);
        Object fieldObject = getField(input, testInstance);
        return cglibMockMaker.getHandler(fieldObject) != null;
    }

    def mockito(Closure closure) {
        try {
            closure.run()
            true
        } catch (Exception ex) {
            throw ex
        }
    }
}
