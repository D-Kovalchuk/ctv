package com.ctv.conference.rest.api.config;

import com.ctv.conference.rest.api.ConferenceController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Dmitry Kovalchuk
 */
@EnableWebMvc
@Configuration
public class RestConfig extends WebMvcConfigurerAdapter {

    @Bean
    public ConferenceController conferenceController() {
        return new ConferenceController();
    }

}
