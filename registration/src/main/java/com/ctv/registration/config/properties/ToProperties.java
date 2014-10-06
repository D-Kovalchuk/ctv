package com.ctv.registration.config.properties;

import org.springframework.beans.factory.annotation.Value;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;
import static org.springframework.security.util.FieldUtils.getFieldValue;

/**
 * @author Timur Yarosh
 */
public class ToProperties {

    public static final String PROPERTY_REGEXP = "[\\$\\{\\}]";

    public Properties toProperties() {
        Field[] fields = getClass().getDeclaredFields();
        Map<String, String> propertiesMap = Stream.of(fields)
                .filter(this::isProperty)
                .collect(toMap(this::getKey, this::getValue));
        return asProperties(propertiesMap);
    }

    private Properties asProperties(Map<String, String> propertiesMap) {
        Properties properties = new Properties();
        properties.putAll(propertiesMap);
        return properties;
    }

    private boolean isProperty(Field field) {
        return field.isAnnotationPresent(Value.class);
    }

    private String getKey(Field field) {
        Value annotation = field.getAnnotation(Value.class);
        return annotation.value().replaceAll(PROPERTY_REGEXP, "");
    }

    private String getValue(Field field) {
        try {
            return String.valueOf(getFieldValue(this, field.getName()));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
