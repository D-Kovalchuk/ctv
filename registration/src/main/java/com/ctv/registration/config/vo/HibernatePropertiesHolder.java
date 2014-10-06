package com.ctv.registration.config.vo;

import org.springframework.beans.factory.annotation.Value;

import java.util.Properties;

/**
 * @author Timur Yarosh
 */
public class HibernatePropertiesHolder implements PropertiesHolder {

    @Value("${hibernate.dialect}")
    private String hibernateDialect;

    @Value("${hibernate.hbm2ddl.auto}")
    private String hbm2ddl;

    @Value("${hibernate.show_sql}")
    private String showSql;

    @Override
    public Properties toProperties() {
        return new ToPropertiesBuilder(this).toProperties();
    }
}
