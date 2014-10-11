package com.ctv.registration.bootstrap.config;

import com.ctv.registration.rest.config.RegistrationSecurityConfig;
import com.ctv.registration.rest.config.RestConfig;
import com.github.isrsal.logging.LoggingFilter;
import com.ctv.registration.persistence.config.PersistenceConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

public class WebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{
                PersistenceConfig.class,
                RegistrationSecurityConfig.class,
                LoggingConfig.class
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
