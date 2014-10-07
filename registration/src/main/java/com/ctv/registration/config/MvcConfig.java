package com.ctv.registration.config;

import com.ctv.registration.core.port.out.RegistrationMvcAdapter;
import com.ctv.registration.mvc.infrastructure.AuthenticationController;
import com.ctv.registration.mvc.infrastructure.RegistrationController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public AuthenticationController authenticationController(AuthenticationManager authenticationManager, RedisOperationsSessionRepository sessionRepository) {
        return new AuthenticationController(authenticationManager, sessionRepository);
    }

    @Bean
    public RegistrationController registrationController(RegistrationMvcAdapter registrationMvcAdapter) {
        return new RegistrationController(registrationMvcAdapter);
    }


}
