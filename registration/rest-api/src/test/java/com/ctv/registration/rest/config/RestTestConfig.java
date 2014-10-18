package com.ctv.registration.rest.config;

import com.ctv.registration.adapter.rest.UserMvcAdapter;
import com.ctv.registration.adapter.rest.UserMvcAdapterImpl;
import com.ctv.registration.rest.AuthenticationController;
import com.ctv.registration.rest.GlobalControllerExceptionHandler;
import com.ctv.registration.rest.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.session.data.redis.RedisOperationsSessionRepository;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import spock.lang.MockingApi;

import javax.annotation.PostConstruct;
import java.util.List;

import static java.util.Arrays.asList;
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
    public UserController registrationController() {
        return new UserController(null);
    }

}
