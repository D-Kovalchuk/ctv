package com.ctv.registration.persistence.config;

import com.ctv.registration.adapter.persistence.config.PersistenceAdapterConfig;
import com.ctv.registration.persistence.config.properties.DataSourcePropertiesHolder;
import com.ctv.registration.persistence.config.properties.HibernatePropertiesHolder;
import com.ctv.registration.persistence.config.properties.PersistencePropertyConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
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
@EnableJpaRepositories(PersistenceConfig.PERSISTENCE_PACKAGE)
@Import({PersistencePropertyConfig.class, PersistenceAdapterConfig.class})
public class PersistenceConfig {

    public static final String PERSISTENCE_PACKAGE = "com.ctv.registration.persistence";
    public static final String ENTITIES_LOCATION = "com.ctv.registration.adapter.persistence.model";

    @Value("${dbInitializerEnabled}")
    private boolean dataInit;

    @Autowired
    private DataSourcePropertiesHolder dataSourcePropertiesHolder;

    @Autowired
    private HibernatePropertiesHolder hibernatePropertiesHolder;

    @Bean
    public DataSource dataSource() {
        Properties dataSourceProperties = dataSourcePropertiesHolder.toProperties();
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
        Properties jpaProperties = hibernatePropertiesHolder.toProperties();
        entityManagerFactoryBean.setJpaProperties(jpaProperties);
        return entityManagerFactoryBean;
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource, ResourceDatabasePopulator databasePopulator) {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setDatabasePopulator(databasePopulator);
        initializer.setEnabled(dataInit);
        return initializer;
    }

    @Bean
    public ResourceDatabasePopulator resourceDatabasePopulator() {
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        Resource resource = new ClassPathResource("data.sql");
        databasePopulator.setScripts(resource);
        databasePopulator.setContinueOnError(true);
        return databasePopulator;
    }

}
