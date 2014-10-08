package com.ctv.config;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.type.AnnotationMetadata;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static org.apache.commons.lang3.ArrayUtils.isEmpty;

/**
 * @author Dmitry Kovalchuk
 */
public class ImportConverterBeanRegistrar implements ImportBeanDefinitionRegistrar {

    private static final Logger log = LoggerFactory.getLogger(ImportConverterBeanRegistrar.class);
    public static final String ANNOTATION_TYPE = EnableConverters.class.getName();
    public static final String VALUE_ATTRIBUTE = "value";

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annotationAttributes = getAnnotationAttributes(importingClassMetadata);
        String[] packages = annotationAttributes.getStringArray(VALUE_ATTRIBUTE);
        if (isEmpty(packages)) {
            log.warn("None packages are specified. Please pass package as a parameter of value property in EnableConverters annotation");
            return;
        }
        Map<String, List<Class<? extends Converter>>> classes = findConverters(packages);
        register(classes, registry);
    }

    private AnnotationAttributes getAnnotationAttributes(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> attributesMap = importingClassMetadata.getAnnotationAttributes(ANNOTATION_TYPE);
        return new AnnotationAttributes(attributesMap);
    }

    private Map<String, List<Class<? extends Converter>>> findConverters(String[] packages) {
        return Stream.of(packages)
                .map(Reflections::new)
                .map(this::toClasses)
                .flatMap(Set::stream)
                .collect(groupingBy(this::name));
    }

    private Set<Class<? extends Converter>> toClasses(Reflections reflection) {
        return reflection.getSubTypesOf(Converter.class);
    }

    private void register(Map<String, List<Class<? extends Converter>>> map, BeanDefinitionRegistry registry) {
        map.forEach((k, v) -> {
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(v.get(0));
            registry.registerBeanDefinition(k, beanDefinition);
        });
    }

    private String name(Class<? extends Converter> c) {
        return c.getName();
    }

}
