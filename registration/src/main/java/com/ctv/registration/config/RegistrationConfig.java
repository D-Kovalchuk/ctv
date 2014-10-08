package com.ctv.registration.config;

import com.ctv.config.converter.EnableConverters;
import com.ctv.registration.core.RegistrationService;
import com.ctv.registration.core.RegistrationServiceImpl;
import com.ctv.registration.core.port.in.UserPersistenceAdapter;
import com.ctv.registration.persistence.adapter.UserPersistenceAdapterImpl;
import com.ctv.registration.persistence.adapter.UserRepository;
import com.ctv.registration.web.adapter.UserMvcAdapter;
import com.ctv.registration.web.adapter.UserMvcAdapterImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Timur Yarosh
 */
@Configuration
@EnableJpaRepositories(RegistrationConfig.PERSISTENCE_PACKAGE)
@EnableConverters("com.ctv.registration.persistence.adapter.converter")
public class RegistrationConfig {

    public static final String PERSISTENCE_PACKAGE = "com.ctv.registration.persistence";

    @Bean
    public UserMvcAdapter registrationMvcAdapter(RegistrationService registrationService) {
        return new UserMvcAdapterImpl(registrationService);
    }

    @Bean
    public RegistrationService registrationService(UserPersistenceAdapter persistenceAdapter) {
        return new RegistrationServiceImpl(persistenceAdapter);
    }

    @Bean
    public UserPersistenceAdapter registrationPersistenceAdapter(UserRepository userRepository, ConversionService conversionService) {
        return new UserPersistenceAdapterImpl(userRepository, conversionService);
    }

}
