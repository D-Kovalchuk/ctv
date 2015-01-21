package com.ctv.conference.core.config;

import com.ctv.conference.core.*;
import com.ctv.conference.core.adapter.ConferencePersistenceAdapter;
import com.ctv.conference.core.adapter.MeetupPersistenceAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Dmitry Kovalchuk
 */
@Configuration
@EnableAspectJAutoProxy
public class CoreConfig {

    @Bean
    public ConferenceService conferenceService(ConferencePersistenceAdapter persistenceAdapter) {
        return new ConferenceServiceImpl(persistenceAdapter);
    }

    @Bean
    public MeetupService meetupService(ConferencePersistenceAdapter conferencePersistenceAdapter, MeetupPersistenceAdapter meetupPersistenceAdapter) {
        return new MeetupServiceImpl(meetupPersistenceAdapter, conferencePersistenceAdapter);
    }

}
