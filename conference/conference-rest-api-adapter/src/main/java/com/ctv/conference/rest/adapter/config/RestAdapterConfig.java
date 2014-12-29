package com.ctv.conference.rest.adapter.config;

import com.ctv.conference.core.ConferenceService;
import com.ctv.conference.core.config.CoreConfig;
import com.ctv.conference.rest.adapter.ConferenceRestAdapter;
import com.ctv.conference.rest.adapter.ConferenceRestAdapterImpl;
import com.ctv.config.converter.EnableConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Dmitry Kovalchuk
 */
@Configuration
@EnableConverters(value = "com.ctv.conference.rest.adapter.converter",
        createConversionService = false, beanName = "mvcConversionService")
@Import(CoreConfig.class)
public class RestAdapterConfig {

    @Bean
    public ConferenceRestAdapter conferenceRestAdapter(ConferenceService conferenceService) {
        return new ConferenceRestAdapterImpl(conferenceService);
    }

}
