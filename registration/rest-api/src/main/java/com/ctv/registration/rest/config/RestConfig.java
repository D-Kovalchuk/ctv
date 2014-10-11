package com.ctv.registration.rest.config;

import com.ctv.registration.adapter.rest.UserMvcAdapter;
import com.ctv.registration.adapter.rest.config.RestAdapterConfig;
import com.ctv.registration.rest.AuthenticationController;
import com.ctv.registration.rest.UserController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author Dmitry Kovalchuk
 */
@EnableWebMvc
@Configuration
@Import(RestAdapterConfig.class)
public class RestConfig  extends WebMvcConfigurerAdapter {

    @Bean
    public AuthenticationController authenticationController(AuthenticationManager authenticationManager, RedisOperationsSessionRepository sessionRepository) {
        return new AuthenticationController(authenticationManager, sessionRepository);
    }

    @Bean
    public UserController registrationController(UserMvcAdapter userMvcAdapter) {
        return new UserController(userMvcAdapter);
    }


}
