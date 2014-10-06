package com.ctv.registration.config;

import com.github.isrsal.logging.LoggingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

public class WebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{
                PersistenceConfig.class,
                RegistrationSecurityConfig.class,
                RegistrationConfig.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{
                MvcConfig.class
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
