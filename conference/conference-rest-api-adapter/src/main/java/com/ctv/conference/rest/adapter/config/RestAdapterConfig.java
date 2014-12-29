package com.ctv.conference.rest.adapter.config;

import com.ctv.conference.rest.adapter.ConferenceAdapter;
import com.ctv.conference.rest.adapter.ConferenceAdapterImpl;
import com.ctv.config.converter.EnableConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Dmitry Kovalchuk
 */
@Configuration
@EnableConverters(value = "com.ctv.conference.rest.adapter.converter",
        createConversionService = false, beanName = "mvcConversionService")
public class RestAdapterConfig {

    @Bean
    public ConferenceAdapter conferenceAdapter() {
        return new ConferenceAdapterImpl();
    }

}
