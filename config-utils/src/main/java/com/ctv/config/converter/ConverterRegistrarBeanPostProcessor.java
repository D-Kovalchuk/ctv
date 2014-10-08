package com.ctv.config.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterRegistry;


/**
 * @author Timur Yarosh
 */
public class ConverterRegistrarBeanPostProcessor implements BeanPostProcessor {

    private static final Logger log = LoggerFactory.getLogger(ConverterRegistrarBeanPostProcessor.class);
    private ConverterRegistry converterRegistry;

    public ConverterRegistrarBeanPostProcessor(ConverterRegistry converterRegistry) {
        this.converterRegistry = converterRegistry;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof Converter) {
            converterRegistry.addConverter((Converter) bean);
            log.debug("Converter '{}' added", bean.getClass());
        }
        return bean;
    }
}
