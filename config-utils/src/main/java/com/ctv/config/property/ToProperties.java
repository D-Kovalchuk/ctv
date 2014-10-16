package com.ctv.config.property;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Properties;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static org.apache.commons.lang3.reflect.FieldUtils.getAllFieldsList;
import static org.apache.commons.lang3.reflect.FieldUtils.readField;

/**
 * @author Timur Yarosh
 */
public class ToProperties {

    public static final String PROPERTY_REGEXP = "[\\$\\{\\}]";
    private static final Logger log = LoggerFactory.getLogger(ToProperties.class);

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
            if (nonNull(value)) {
                if (value instanceof String) {
                    return isNotEmpty((CharSequence) value);
                } else {
                    return true;
                }
            }
        } catch (IllegalAccessException e) {
            // never happen
        }
        return false;
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
