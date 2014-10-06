package com.ctv.registration.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.Properties;
import java.util.stream.Stream;

import static java.util.AbstractMap.SimpleImmutableEntry;
import static java.util.Map.Entry;
import static org.springframework.security.util.FieldUtils.getFieldValue;

/**
 * @author Timur Yarosh
 */
@Configuration
@PropertySource("classpath:" + PersistenceConfig.PERSISTENCE_PROPERTIES)
@PropertySource(value = "file:${user.home}/.config/ctv/" + PersistenceConfig.PERSISTENCE_PROPERTIES, ignoreResourceNotFound = true)
@EnableJpaRepositories(PersistenceConfig.PERSISTENCE_PACKAGE)
public class PersistenceConfig {

    public static final String PERSISTENCE_PROPERTIES = "persistence.properties";
    public static final String PERSISTENCE_PACKAGE = "com.ctv.registration.persistence";

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig(dataSourcePropertiesHolder().toProperties());
        config.setDataSourceClassName(dataSourcePropertiesHolder().dataSourceClassName);
        return new HikariDataSource(config);
    }

    @Bean
    public JpaTransactionManager jpaTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan(PERSISTENCE_PACKAGE);
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setJpaProperties(hibernatePropertiesHolder().toProperties());
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

    private class DataSourcePropertiesHolder extends PropertiesHolder {

        @Value("${connectionTestQuery}")
        private String connectionTestQuery;

        @Value("${maximumPoolSize}")
        private String maximumPoolSize;

        @Value("${dataSource.user}")
        private String jdbcUser;

        @Value("${dataSource.password}")
        private String jdbcPassword;

        @Value("${dataSource.databaseName}")
        private String databaseName;

        @Value("${dataSource.serverName}")
        private String host;

        @Value("${dataSourceClassName}")
        private String dataSourceClassName;

    }

    private class HibernatePropertiesHolder extends PropertiesHolder {

        @Value("${hibernate.dialect}")
        private String hibernateDialect;

        @Value("${hibernate.hbm2ddl.auto}")
        private String hbm2ddl;

        @Value("${hibernate.show_sql}")
        private String showSql;

    }

    private class PropertiesHolder {
        public Properties toProperties() {
            Properties properties = new Properties();
            Field[] declaredFields = getClass().getDeclaredFields();
            Stream.of(declaredFields)
                    .filter(f -> f.isAnnotationPresent(Value.class))
                    .map(this::toEntry)
                    .forEach(e -> properties.put(e.getKey(), e.getValue()));
            return properties;
        }

        private Entry toEntry(Field field) {
            try {
                Value annotation = field.getAnnotation(Value.class);
                String propertyName = annotation.value().replaceAll("[\\$\\{\\}]", "");
                String propertyValue = String.valueOf(getFieldValue(this, field.getName()));
                return new SimpleImmutableEntry<>(propertyName, propertyValue);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
