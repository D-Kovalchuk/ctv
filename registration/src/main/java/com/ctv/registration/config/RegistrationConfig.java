package com.ctv.registration.config;

import com.ctv.registration.core.port.out.RegistrationMvcAdapter;
import com.ctv.registration.web.adapter.RegistrationMvcAdapterImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Timur Yarosh
 */
@Configuration
public class RegistrationConfig {

    @Bean
    public RegistrationMvcAdapter registrationMvcAdapter() {
        return new RegistrationMvcAdapterImpl();
    }

}
