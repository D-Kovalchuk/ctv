package com.ctv.conference.rest.api.config;

import com.ctv.conference.rest.adapter.config.RestAdapterConfig;
import com.ctv.conference.rest.api.ConferenceController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Dmitry Kovalchuk
 */
@EnableWebMvc
@Configuration
@Import(RestAdapterConfig.class)
public class RestConfig extends WebMvcConfigurerAdapter {

    @Bean
    public ConferenceController conferenceController() {
        return new ConferenceController();
    }

}
