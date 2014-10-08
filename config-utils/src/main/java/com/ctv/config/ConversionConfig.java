package com.ctv.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.ConverterRegistry;

/**
 * @author Timur Yarosh
 */
@Configuration
public class ConversionConfig {

    @Bean
    public ConversionServiceFactoryBean conversionService() {
        return new ConversionServiceFactoryBean();
    }

    @Bean
    public static ConverterRegistrarBeanPostProcessor registrarBeanPostProcessor(ConversionService conversionService) {
        return new ConverterRegistrarBeanPostProcessor((ConverterRegistry) conversionService);
    }

}
