package com.ctv.registration.config;

import com.ctv.registration.controller.AuthenticationController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@EnableWebMvc
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {


    @Bean
    public AuthenticationController authenticationController(AuthenticationManager authenticationManager, RedisOperationsSessionRepository sessionRepository) {
        return new AuthenticationController(authenticationManager, sessionRepository);
    }


    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(APPLICATION_JSON);
    }
}
