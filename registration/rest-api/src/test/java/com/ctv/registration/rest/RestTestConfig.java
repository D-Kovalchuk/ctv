package com.ctv.registration.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author Dmitry Kovalchuk
 */
@EnableWebMvc
@Configuration
public class RestTestConfig {

    @Bean
    public AuthenticationController authenticationController(AuthenticationManager authenticationManager, RedisOperationsSessionRepository sessionRepository) {
        return new AuthenticationController(authenticationManager, sessionRepository);
    }

    @Bean
    public GlobalControllerExceptionHandler globalControllerExceptionHandler() {
        return new GlobalControllerExceptionHandler();
    }


}
