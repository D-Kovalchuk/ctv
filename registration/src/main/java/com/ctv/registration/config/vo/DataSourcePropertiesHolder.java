package com.ctv.registration.config.vo;

import org.springframework.beans.factory.annotation.Value;

import java.util.Properties;

/**
 * @author Timur Yarosh
 */
public class DataSourcePropertiesHolder implements PropertiesHolder {

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

    @Override
    public Properties toProperties() {
        return new ToPropertiesBuilder(this).toProperties();
    }
}
