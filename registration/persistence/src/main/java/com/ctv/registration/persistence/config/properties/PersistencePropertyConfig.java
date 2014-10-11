package com.ctv.registration.persistence.config.properties;

import com.ctv.config.property.EnablePropertySource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Dmitry Kovalchuk
 */
@EnablePropertySource
@PropertySource("classpath:" + PersistencePropertyConfig.PERSISTENCE_DEFAULT_PROPERTIES)
@PropertySource(value = "file:" + PersistencePropertyConfig.PATH_TO_USER_PROPERTIES
        + PersistencePropertyConfig.PERSISTENCE_PROPERTIES, ignoreResourceNotFound = true)
public class PersistencePropertyConfig {

    public static final String PATH_TO_USER_PROPERTIES = "${user.home}/.config/ctv/";
    public static final String PERSISTENCE_DEFAULT_PROPERTIES = "persistence-default.properties";
    public static final String PERSISTENCE_PROPERTIES = "persistence.properties";

    @Bean
    public DataSourcePropertiesHolder dataSourcePropertiesHolder() {
        return new DataSourcePropertiesHolder();
    }

    @Bean
    public HibernatePropertiesHolder hibernatePropertiesHolder() {
        return new HibernatePropertiesHolder();
    }

}
