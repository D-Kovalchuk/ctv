package com.ctv.config.property;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.reflect.FieldUtils.getAllFieldsList;
import static org.apache.commons.lang3.reflect.FieldUtils.readField;

/**
 * @author Timur Yarosh
 */
public class ToProperties {

    public static final String PROPERTY_REGEXP = "[\\$\\{\\}]";

    public Properties toProperties() {
        Map<String, String> propertiesMap = getAllFieldsList(getClass()).stream()
                .filter(this::isProperty)
                .filter(this::notBlank)
                .collect(toMap(this::getKey, this::getValue));
        return asProperties(propertiesMap);
    }

    private boolean notBlank(Field field) {
        try {
            Object value = readField(field, this, true);
            if (Objects.nonNull(value)) {
                return StringUtils.isNotEmpty((CharSequence) value);
            }
            return false;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
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
        return annotation.value().replaceAll(PROPERTY_REGEXP, EMPTY);
    }

    private String getValue(Field field) {
        try {
            return String.valueOf(readField(field, this, true));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
