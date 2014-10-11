package com.ctv.registration.adapter.rest.config;

import com.ctv.config.converter.EnableConverters;
import com.ctv.registration.adapter.rest.UserMvcAdapter;
import com.ctv.registration.adapter.rest.UserMvcAdapterImpl;
import com.ctv.registration.core.RegistrationService;
import com.ctv.registration.core.config.CoreConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.ConversionService;

/**
 * @author Dmitry Kovalchuk
 */
@Configuration
@Import(CoreConfig.class)
@EnableConverters(value = "com.ctv.registration.adapter.rest.converter",
        createConversionService = false, beanName = "mvcConversionService")
public class RestAdapterConfig {

    @Bean
    public UserMvcAdapter registrationMvcAdapter(RegistrationService registrationService, ConversionService conversionService) {
        return new UserMvcAdapterImpl(registrationService, conversionService);
    }

}
