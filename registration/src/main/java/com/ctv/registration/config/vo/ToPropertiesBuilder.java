package com.ctv.registration.config.vo;

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
public class ToPropertiesBuilder implements PropertiesHolder {

    private Object propertiesHolder;

    public ToPropertiesBuilder(Object propertiesHolder) {
        this.propertiesHolder = propertiesHolder;
    }

    @Override
    public Properties toProperties() {
        Properties properties = new Properties();
        Field[] declaredFields = propertiesHolder.getClass().getDeclaredFields();

        Map<String, String> propertiesMap = Stream.of(declaredFields)
                .filter(this::isProperty)
                .collect(toMap(this::getKey, this::getValue));

        properties.putAll(propertiesMap);
        return properties;
    }

    private boolean isProperty(Field field) {
        return field.isAnnotationPresent(Value.class);
    }

    private String getKey(Field field) {
        Value annotation = field.getAnnotation(Value.class);
        return annotation.value().replaceAll("[\\$\\{\\}]", "");
    }

    private String getValue(Field field) {
        try {
            return String.valueOf(getFieldValue(propertiesHolder, field.getName()));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
