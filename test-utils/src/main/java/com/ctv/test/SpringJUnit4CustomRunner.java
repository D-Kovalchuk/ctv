package com.ctv.test;

/**
 * @author Dmitry Kovalchuk
 */

import org.junit.runners.model.InitializationError;
import org.mockito.Mockito;
import org.mockito.internal.creation.CglibMockMaker;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.stream.Stream;

import static org.springframework.util.ReflectionUtils.getField;

/**
 * Custom runner which extends from <code>SpringJUnit4ClassRunner</code>. It adds an additional functionality, specifically it makes reset
 * for all of fields which where created by Mockito. That way client of this class don't need to do reset of mocks in <code>After</code>
 * block.
 */
public class SpringJUnit4CustomRunner extends SpringJUnit4ClassRunner {

    private CglibMockMaker cglibMockMaker = new CglibMockMaker();

    /**
     * Constructs a new {@code SpringJUnit4ClassRunner} and initializes a {@link org.springframework.test.context.TestContextManager} to
     * provide Spring testing functionality to standard JUnit tests.
     *
     * @param clazz
     *            the test class to be run
     * @see #createTestContextManager(Class)
     */
    public SpringJUnit4CustomRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }

    @Override
    protected TestContextManager createTestContextManager(final Class<?> clazz) {
        return new TestContextManager(clazz) {
            @Override
            public void afterTestMethod(Object testInstance, Method testMethod, Throwable exception) throws Exception {
                super.afterTestMethod(testInstance, testMethod, exception);
                Stream<Field> fieldStream = Stream.of(clazz.getDeclaredFields());
                fieldStream
                        .filter(field -> isMock(testInstance, field))
                        .map(field -> fieldToObject(testInstance, field))
                        .forEach(Mockito::reset);
            }
        };
    }

    private Object fieldToObject(final Object testInstance, Field input) {
        return getField(input, testInstance);
    }

    private boolean isMock(final Object testInstance, Field input) {
        input.setAccessible(true);
        Object fieldObject = getField(input, testInstance);
        return cglibMockMaker.getHandler(fieldObject) != null;
    }
}