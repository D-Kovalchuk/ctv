package com.ctv.registration.bootstrap.config;

import com.ctv.registration.bootstrap.LogTracer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.interceptor.CustomizableTraceInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Timur Yarosh
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class LoggingConfig {

    @Bean
    public CustomizableTraceInterceptor customizableTraceInterceptor() {
        CustomizableTraceInterceptor customizableTraceInterceptor = new LogTracer(new ObjectMapper());
        customizableTraceInterceptor.setUseDynamicLogger(true);
        customizableTraceInterceptor.setEnterMessage("Entering $[targetClassShortName].$[methodName]($[arguments])");
        customizableTraceInterceptor.setExitMessage("Leaving $[targetClassShortName].$[methodName](), returned $[returnValue]");
        return customizableTraceInterceptor;
    }

    @Bean
    public Advisor logAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("within(com.ctv..*) && !within(*.config..*)");
        return new DefaultPointcutAdvisor(pointcut, customizableTraceInterceptor());
    }

}
