package com.ctv.registration.core.config;

import com.ctv.registration.core.RegistrationService;
import com.ctv.registration.core.RegistrationServiceImpl;
import com.ctv.registration.core.adapter.UserPersistenceAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Dmitry Kovalchuk
 */
@Configuration
public class CoreConfig {

    @Bean
    public RegistrationService registrationService(UserPersistenceAdapter persistenceAdapter) {
        return new RegistrationServiceImpl(persistenceAdapter);
    }

}
