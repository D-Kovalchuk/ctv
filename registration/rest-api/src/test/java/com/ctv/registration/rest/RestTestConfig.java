package com.ctv.registration.rest;

import com.ctv.registration.adapter.rest.UserMvcAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import static org.mockito.Mockito.mock;

/**
 * @author Dmitry Kovalchuk
 */
@EnableWebMvc
@Configuration
public class RestTestConfig extends WebMvcConfigurerAdapter {

    @Bean
    public AuthenticationController authenticationController(AuthenticationManager authenticationManager, RedisOperationsSessionRepository sessionRepository) {
        return new AuthenticationController(authenticationManager, sessionRepository);
    }

    @Bean
    public GlobalControllerExceptionHandler globalControllerExceptionHandler() {
        return new GlobalControllerExceptionHandler();
    }

    @Bean
    public UserController registrationController(UserMvcAdapter userMvcAdapter) {
        return new UserController(userMvcAdapter);
    }

    @Bean
    public UserMvcAdapter mockUserMvcAdapter() {
        return mock(UserMvcAdapter.class);
    }

}
