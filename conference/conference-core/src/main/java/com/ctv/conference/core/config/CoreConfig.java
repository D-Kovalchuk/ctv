package com.ctv.conference.core.config;

import com.ctv.conference.core.ConferenceService;
import com.ctv.conference.core.ConferenceServiceImpl;
import com.ctv.conference.core.adapter.ConferencePersistenceAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Dmitry Kovalchuk
 */
@Configuration
public class CoreConfig {

    @Bean
    public ConferenceService conferenceService(ConferencePersistenceAdapter persistenceAdapter) {
        return new ConferenceServiceImpl(persistenceAdapter);
    }

}
