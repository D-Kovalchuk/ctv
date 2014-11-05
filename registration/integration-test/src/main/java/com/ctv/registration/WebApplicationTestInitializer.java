package com.ctv.registration;

import com.ctv.registration.core.config.RegistrationSecurityConfig;
import com.ctv.registration.rest.config.RestConfig;
import com.github.isrsal.logging.LoggingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * @author Dmitry Kovalchuk
 */
public class WebApplicationTestInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{
                PersistenceTestConfig.class,
                RegistrationSecurityConfig.class,
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{
                RestConfig.class
        };
    }

    @Override
    protected Filter[] getServletFilters() {
        return new Filter[]{
                new LoggingFilter()
        };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

}
