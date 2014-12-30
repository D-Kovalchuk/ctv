package com.ctv.conference.persistence.config;

import com.ctv.conference.persistence.SpringConferenceRepository;
import com.ctv.conference.persistence.adapter.ConferenceRepository;
import com.ctv.conference.persistence.adapter.config.PersistenceAdapterConfig;
import com.ctv.config.property.EnablePropertySource;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoOperations;

/**
 * @author Dmitry Kovalchuk
 */
@Configuration
@EnablePropertySource
@Import(PersistenceAdapterConfig.class)
@PropertySource("classpath:persistence-default.properties")
@PropertySource(value = "file:${user.home}/.config/ctv/persistence.properties", ignoreResourceNotFound = true)
public class PersistenceConfig extends AbstractMongoConfiguration {

    @Value("${host}")
    private String host;

    @Value("${port}")
    private int port;

    @Value("${dbName}")
    private String dbName;

    @Bean
    public ConferenceRepository conferenceRepository(MongoOperations mongoOperations) {
        return new SpringConferenceRepository(mongoOperations);
    }

    @Override
    protected String getDatabaseName() {
        return dbName;
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient(host, port);
    }
}
