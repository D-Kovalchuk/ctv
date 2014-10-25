package com.ctv.registration.persistence.config.properties;

import com.ctv.config.property.EnablePropertySource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Dmitry Kovalchuk
 */
@EnablePropertySource
@PropertySource("classpath:persistence-default.properties")
@PropertySource(value = "file:${user.home}/.config/ctv/persistence.properties", ignoreResourceNotFound = true)
public class PersistencePropertyConfig {


    @Bean
    public DataSourcePropertiesHolder dataSourcePropertiesHolder() {
        return new DataSourcePropertiesHolder();
    }

    @Bean
    public HibernatePropertiesHolder hibernatePropertiesHolder() {
        return new HibernatePropertiesHolder();
    }

}
