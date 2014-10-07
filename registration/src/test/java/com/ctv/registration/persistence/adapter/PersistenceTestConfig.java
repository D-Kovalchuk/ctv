package com.ctv.registration.persistence.adapter;

import com.ctv.registration.config.PersistenceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

import static com.ctv.registration.config.PersistenceConfig.PERSISTENCE_DEFAULT_PROPERTIES;

/**
 * @author Dmitry Kovalchuk
 */
@Configuration
@Import(PersistenceConfig.class)
@PropertySource("classpath:" + PERSISTENCE_DEFAULT_PROPERTIES)
@PropertySource("classpath:" + PersistenceTestConfig.PERSISTENCE_TEST_PROPERTIES)
public class PersistenceTestConfig {

    public static final String PERSISTENCE_TEST_PROPERTIES = "persistence-test.properties";

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .build();
    }

}
