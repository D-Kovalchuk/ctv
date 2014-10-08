package com.ctv.config;

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

}
