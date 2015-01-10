package com.ctv.conference.core.config;

import com.ctv.conference.core.ConferenceService;
import com.ctv.conference.core.ConferenceServiceImpl;
import com.ctv.conference.core.SecurityAspect;
import com.ctv.conference.core.adapter.ConferencePersistenceAdapter;
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
    public SecurityAspect securityAspect() {
        return new SecurityAspect();
    }

}
