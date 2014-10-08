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
@Import(ConversionConfig.class)
public @interface EnableConverters {

    //TODO array of packages with converters which should be added as beans and registered to ConverterRegistry
//    String[] value() default {};

}
