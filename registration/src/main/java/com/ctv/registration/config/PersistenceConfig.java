package com.ctv.registration.config;

import com.ctv.config.EnablePropertySource;
import com.ctv.registration.config.properties.DataSourcePropertiesHolder;
import com.ctv.registration.config.properties.HibernatePropertiesHolder;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author Timur Yarosh
 */
@Configuration
@EnableTransactionManagement
@EnablePropertySource
@PropertySource("classpath:" + PersistenceConfig.PERSISTENCE_DEFAULT_PROPERTIES)
@PropertySource(value = "file:${user.home}/.config/ctv/" + PersistenceConfig.PERSISTENCE_PROPERTIES, ignoreResourceNotFound = true)
public class PersistenceConfig {

    public static final String PERSISTENCE_DEFAULT_PROPERTIES = "persistence-default.properties";
    public static final String PERSISTENCE_PROPERTIES = "persistence.properties";
    public static final String ENTITIES_LOCATION = "com.ctv.registration.persistence.adapter.model";

    @Bean
    public DataSource dataSource() {
        Properties dataSourceProperties = dataSourcePropertiesHolder().toProperties();
        HikariConfig config = new HikariConfig(dataSourceProperties);
        return new HikariDataSource(config);
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        EntityManagerFactory entityManagerFactory = entityManagerFactory().getObject();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan(ENTITIES_LOCATION);
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        Properties jpaProperties = hibernatePropertiesHolder().toProperties();
        entityManagerFactoryBean.setJpaProperties(jpaProperties);
        return entityManagerFactoryBean;
    }

    @Bean
    public DataSourcePropertiesHolder dataSourcePropertiesHolder() {
        return new DataSourcePropertiesHolder();
    }

    @Bean
    public HibernatePropertiesHolder hibernatePropertiesHolder() {
        return new HibernatePropertiesHolder();
    }

}
