package com.ctv.registration.config;

import com.ctv.registration.core.RegistrationService;
import com.ctv.registration.core.RegistrationServiceImpl;
import com.ctv.registration.core.port.in.RegistrationPersistenceAdapter;
import com.ctv.registration.persistence.adapter.RegistrationPersistenceAdapterImpl;
import com.ctv.registration.persistence.adapter.UserRepository;
import com.ctv.registration.web.adapter.RegistrationMvcAdapter;
import com.ctv.registration.web.adapter.RegistrationMvcAdapterImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Timur Yarosh
 */
@Configuration
@EnableJpaRepositories(RegistrationConfig.PERSISTENCE_PACKAGE)
public class RegistrationConfig {

    public static final String PERSISTENCE_PACKAGE = "com.ctv.registration.persistence";

    @Bean
    public RegistrationMvcAdapter registrationMvcAdapter(RegistrationService registrationService) {
        return new RegistrationMvcAdapterImpl(registrationService);
    }

    @Bean
    public RegistrationService registrationService(RegistrationPersistenceAdapter persistenceAdapter) {
        return new RegistrationServiceImpl(persistenceAdapter);
    }

    @Bean
    public RegistrationPersistenceAdapter registrationPersistenceAdapter(UserRepository userRepository, ConversionService conversionService) {
        return new RegistrationPersistenceAdapterImpl(userRepository, conversionService);
    }

    @Bean
    public ConversionServiceFactoryBean conversionService() {
        return new ConversionServiceFactoryBean();
    }

}
