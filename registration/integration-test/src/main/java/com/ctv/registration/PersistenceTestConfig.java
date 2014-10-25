package com.ctv.registration;

import com.ctv.registration.persistence.config.PersistenceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Dmitry Kovalchuk
 */
@Configuration
@PropertySource("classpath:persistence-default.properties")
@PropertySource("classpath:persistence-test.properties")
public class PersistenceTestConfig extends PersistenceConfig {
}
