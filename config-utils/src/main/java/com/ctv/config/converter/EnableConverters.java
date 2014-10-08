package com.ctv.config.converter;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Timur Yarosh
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({ImportConverterBeanRegistrar.class, ConversionConfig.class})
public @interface EnableConverters {

    String[] value();

    boolean createConversionService() default true;

    String beanName() default "conversionService";

}
