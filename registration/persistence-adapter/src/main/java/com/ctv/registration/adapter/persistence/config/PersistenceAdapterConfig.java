package com.ctv.registration.adapter.persistence.config;

import com.ctv.config.converter.EnableConverters;
import com.ctv.registration.adapter.persistence.UserPersistenceAdapterImpl;
import com.ctv.registration.adapter.persistence.api.UserRepository;
import com.ctv.registration.core.adapter.UserPersistenceAdapter;
import com.ctv.registration.core.config.CoreConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.ConversionService;

/**
 * @author Dmitry Kovalchuk
 */
@Configuration
@EnableConverters("com.ctv.registration.adapter.persistence.converter")
@Import(CoreConfig.class)
public class PersistenceAdapterConfig {

    @Bean
    public UserPersistenceAdapter userPersistenceAdapter(UserRepository userRepository, ConversionService conversionService) {
        return new UserPersistenceAdapterImpl(userRepository, conversionService);
    }

}
