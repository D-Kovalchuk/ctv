package com.ctv.config.converter;

import org.reflections.Reflections;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.context.support.ConversionServiceFactoryBean;
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
class ImportConverterBeanRegistrar implements ImportBeanDefinitionRegistrar {

    public static final String ANNOTATION_TYPE = EnableConverters.class.getName();
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String CREATE_CONVERSION_SERVICE = "createConversionService";
    public static final String BEAN_NAME = "beanName";
    private BeanDefinitionRegistry registry;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        this.registry = registry;
        AnnotationAttributes annotationAttributes = getAnnotationAttributes(importingClassMetadata);
        String[] packages = annotationAttributes.getStringArray(VALUE_ATTRIBUTE);
        if (isEmpty(packages)) {
            throw new ApplicationContextException("None packages are specified. Please pass package as a parameter of value property in EnableConverters annotation");
        }
        boolean createConversionService = annotationAttributes.getBoolean(CREATE_CONVERSION_SERVICE);
        String conversionServiceBeanName = annotationAttributes.getString(BEAN_NAME);
        registerConversionService(conversionServiceBeanName, createConversionService);
        registerConverters(packages);
    }



    private void registerConversionService(String conversionServiceBeanName, boolean createConversionService) {
        if (createConversionService) {
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(ConversionServiceFactoryBean.class);
            registry.registerBeanDefinition(conversionServiceBeanName, beanDefinition);
        }
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
                .collect(groupingBy(Class::getName));
    }

    private Set<Class<? extends Converter>> toClasses(Reflections reflection) {
        return reflection.getSubTypesOf(Converter.class);
    }

    private void registerConverters(String[] packages) {
        findConverters(packages).forEach((k, v) -> {
            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(v.get(0));
            registry.registerBeanDefinition(k, beanDefinition);
        });
    }

}
