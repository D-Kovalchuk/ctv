package com.ctv.registration.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;

/**
 * @author Dmitry Kovalchuk
 */
public class AlwaysReauthenticateBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof AbstractSecurityInterceptor) {
            AbstractSecurityInterceptor securityInterceptor = (AbstractSecurityInterceptor) bean;
            securityInterceptor.setAlwaysReauthenticate(true);
        }
        return bean;
    }

}
