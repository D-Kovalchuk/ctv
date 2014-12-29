package com.ctv.conference.persistence.config;

import com.ctv.conference.persistence.adapter.config.PersistenceAdapterConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Dmitry Kovalchuk
 */
@Configuration
@Import(PersistenceAdapterConfig.class)
public class PersistenceConfig {
}
