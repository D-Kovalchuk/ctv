package com.ctv.conference.persistence.adapter.config;

import com.ctv.conference.core.adapter.ConferencePersistenceAdapter;
import com.ctv.conference.core.config.CoreConfig;
import com.ctv.conference.persistence.adapter.ConferencePersistenceAdapterImpl;
import com.ctv.conference.persistence.adapter.ConferenceRepository;
import com.ctv.config.converter.EnableConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Dmitry Kovalchuk
 */
@Configuration
@Import(CoreConfig.class)
@EnableConverters("com.ctv.conference.persistence.adapter.converter")
public class PersistenceAdapterConfig {

    @Bean
    public ConferencePersistenceAdapter conferencePersistenceAdapter(ConferenceRepository conferenceRepository) {
        return new ConferencePersistenceAdapterImpl(conferenceRepository);
    }

}
