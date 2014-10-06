package com.ctv.config;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Dmitry Kovalchuk
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(PropertySourceConfig.class)
public @interface EnablePropertySource {
}
