package com.ctv.conference.core.config;

import com.ctv.conference.core.adapter.ConferencePersistenceAdapter;
import com.ctv.conference.core.adapter.MeetupPersistenceAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.mockito.Mockito.mock;

/**
 * @author Dmitry Kovalchuk
 */
@Configuration
@Import(CoreConfig.class)
public class CoreTestConfig {

    @Bean
    public ConferencePersistenceAdapter persistenceAdapter() {
        return mock(ConferencePersistenceAdapter.class);
    }

    @Bean
    public MeetupPersistenceAdapter meetupPersistenceAdapter() {
        return mock(MeetupPersistenceAdapter.class);
    }

}
