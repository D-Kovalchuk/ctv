package com.ctv.registration;

import com.ctv.registration.persistence.config.PersistenceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

import static com.ctv.registration.persistence.config.properties.PersistencePropertyConfig.PERSISTENCE_DEFAULT_PROPERTIES;

/**
 * @author Dmitry Kovalchuk
 */
@Configuration
@PropertySource("classpath:" + PERSISTENCE_DEFAULT_PROPERTIES)
@PropertySource("classpath:persistence-test.properties")
public class PersistenceTestConfig extends PersistenceConfig {

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL).build();
    }
}
