package com.ctv.conference.bootstrap;

import com.ctv.conference.persistence.config.PersistenceConfig;
import com.ctv.conference.rest.api.config.RestConfig;
import com.github.isrsal.logging.LoggingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * @author Dmitry Kovalchuk
 */
public class WebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{
                PersistenceConfig.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] {
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
