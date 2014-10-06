package com.ctv.registration.config.properties;

import com.ctv.config.ToProperties;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Timur Yarosh
 */
public class DataSourcePropertiesHolder extends ToProperties {

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
