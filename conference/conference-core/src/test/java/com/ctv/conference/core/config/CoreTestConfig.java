package com.ctv.conference.core.config;

import com.ctv.conference.core.adapter.*;
import com.ctv.conference.core.validation.ConferenceSecurityRule;
import com.ctv.conference.core.validation.MeetupSecurityRule;
import com.ctv.conference.core.validation.TalkSecurityRule;
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

    @Bean
    public TalkPersistenceAdapter talkPersistenceAdapter() {
        return mock(TalkPersistenceAdapter.class);
    }

    @Bean
    public ConferenceSecurityRule conferenceSecurityRule() {
        return mock(ConferenceSecurityRule.class);
    }

    @Bean
    public MeetupSecurityRule meetupSecurityRule() {
        return mock(MeetupSecurityRule.class);
    }

    @Bean
    public TalkSecurityRule taskSecurityRule() {
        return mock(TalkSecurityRule.class);
    }

}
