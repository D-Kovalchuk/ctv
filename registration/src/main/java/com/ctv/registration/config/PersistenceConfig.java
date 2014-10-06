package com.ctv.registration.config;

import com.ctv.registration.config.properties.DataSourcePropertiesHolder;
import com.ctv.registration.config.properties.HibernatePropertiesHolder;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author Timur Yarosh
 */
@Configuration
@PropertySource("classpath:" + PersistenceConfig.PERSISTENCE_DEFAULT_PROPERTIES)
@PropertySource(value = "file:${user.home}/.config/ctv/" + PersistenceConfig.PERSISTENCE_PROPERTIES, ignoreResourceNotFound = true)
@EnableJpaRepositories(PersistenceConfig.PERSISTENCE_PACKAGE)
public class PersistenceConfig {

    public static final String PERSISTENCE_DEFAULT_PROPERTIES = "persistence-default.properties";
    public static final String PERSISTENCE_PROPERTIES = "persistence.properties";
    public static final String PERSISTENCE_PACKAGE = "com.ctv.registration.persistence";

    @Bean
    public DataSource dataSource() {
        Properties dataSourceProperties = dataSourcePropertiesHolder().toProperties();
        HikariConfig config = new HikariConfig(dataSourceProperties);
        return new HikariDataSource(config);
    }

    @Bean
    public JpaTransactionManager jpaTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        EntityManagerFactory entityManagerFactory = entityManagerFactory().getObject();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan(PERSISTENCE_PACKAGE);
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        Properties jpaProperties = hibernatePropertiesHolder().toProperties();
        entityManagerFactoryBean.setJpaProperties(jpaProperties);
        return entityManagerFactoryBean;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
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
