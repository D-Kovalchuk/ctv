package com.ctv.registration.config.properties;

import com.ctv.config.property.ToProperties;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Timur Yarosh
 */
public class HibernatePropertiesHolder extends ToProperties {

    @Value("${hibernate.dialect}")
    private String hibernateDialect;

    @Value("${hibernate.hbm2ddl.auto}")
    private String hbm2ddl;

    @Value("${hibernate.show_sql}")
    private String showSql;

}
