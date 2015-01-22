package com.ctv.conference.core.config;

import com.ctv.conference.core.*;
import com.ctv.conference.core.adapter.*;
import com.ctv.conference.core.validation.ConferenceSecurityRule;
import com.ctv.conference.core.validation.MeetupSecurityRule;
import com.ctv.conference.core.validation.TalkSecurityRule;
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
    public ConferenceService conferenceService(ConferencePersistenceAdapter persistenceAdapter, ValidationService validationService) {
        return new ConferenceServiceImpl(persistenceAdapter, validationService);
    }

    @Bean
    public MeetupService meetupService(MeetupPersistenceAdapter meetupPersistenceAdapter, ValidationService validationService) {
        return new MeetupServiceImpl(meetupPersistenceAdapter, validationService);
    }

    @Bean
    public TalkService talkService(TalkPersistenceAdapter talkPersistenceAdapter, ValidationService validationService) {
        return new TalkServiceImpl(talkPersistenceAdapter, validationService);
    }

    @Bean
    public ValidationService validationService(ConferenceSecurityRule conferenceSecurityRule, MeetupSecurityRule meetupSecurityRule, TalkSecurityRule talkSecurityRule) {
        return new ValidationServiceImpl(conferenceSecurityRule, meetupSecurityRule, talkSecurityRule);
    }

}
