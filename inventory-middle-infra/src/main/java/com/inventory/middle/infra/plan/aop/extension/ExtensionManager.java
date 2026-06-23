package com.inventory.middle.infra.plan.aop.extension;

import com.google.common.collect.Maps;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * {@link ExtensionProcessor} 后置处理器
 *
 * @author Danny.Lee
 * @date 2022/4/15
 */
@Component
@Slf4j
@SuppressWarnings("unchecked")
public class ExtensionManager implements BeanPostProcessor {

    private static final String DEFAULT_TENANT = "1369923265280311297";

    private static final Map<ExtensionCoordinate, ExtensionProcessor> EXTENSIONS = Maps.newHashMap();

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> extensionClass;

        if ((extensionClass = isCandidateExtension(bean)) == null) {
            return bean;
        }

        Extension extension = AnnotationUtils.findAnnotation(bean.getClass(), Extension.class);
        Assert.notNull(extension, "Extension annotation must not be null on bean: " + beanName);
        List<ExtensionCoordinate> coordinates = ExtensionCoordinate.of(extensionClass, extension.value());
        for (ExtensionCoordinate coordinate : coordinates) {
            ExtensionProcessor existing = EXTENSIONS.putIfAbsent(coordinate, (ExtensionProcessor) bean);
            Assert.state(existing == null,
                    "Extension adapter conflict: extensionClass=" + extensionClass + ", tenant=" + Arrays.toString(extension.value()));
        }
        return bean;
    }

    private Class<?> isCandidateExtension(Object bean) {
        if (AnnotationUtils.findAnnotation(bean.getClass(), Extension.class) == null) {
            return null;
        }
        return findExtensionProcessorClass(bean);
    }

    private Class<?> findExtensionProcessorClass(Object bean) {
        if (!(bean instanceof ExtensionProcessor)) {
            return null;
        }
        Class<?>[] interfaces = bean.getClass().getInterfaces();
        for (Class<?> intf : interfaces) {
            if (ExtensionProcessor.class.isAssignableFrom(intf)) {
                return intf;
            }
        }
        Class<?> superClass = bean.getClass().getSuperclass();
        while (Object.class != superClass) {
            interfaces = superClass.getInterfaces();
            for (Class<?> intf : interfaces) {
                if (ExtensionProcessor.class.isAssignableFrom(intf)) {
                    return intf;
                }
            }
            superClass = superClass.getSuperclass();
        }
        return null;
    }

    public static <T extends ExtensionProcessor> T findExtension(Class<T> extensionClass, String tenant) {
        ExtensionCoordinate coordinate = ExtensionCoordinate.of(extensionClass, tenant);
        ExtensionProcessor processor = EXTENSIONS.getOrDefault(
                coordinate, EXTENSIONS.get(ExtensionCoordinate.of(extensionClass, DEFAULT_TENANT)));
        Assert.notNull(processor,
                "Cannot find extension adapter: extensionClass=" + extensionClass.getName() + ", tenant=" + tenant);
        return (T) processor;
    }

    @Data
    private static final class ExtensionCoordinate {
        private final Class<?> extensionClass;
        private final String tenant;

        private ExtensionCoordinate(Class<?> extensionClass, String tenant) {
            this.extensionClass = extensionClass;
            this.tenant = tenant;
        }

        public static List<ExtensionCoordinate> of(Class<?> extensionClass, String[] tenants) {
            return Arrays.stream(tenants).distinct()
                    .map(t -> new ExtensionCoordinate(extensionClass, t))
                    .collect(Collectors.toList());
        }

        public static ExtensionCoordinate of(Class<?> extensionClass, String tenant) {
            return new ExtensionCoordinate(extensionClass, tenant);
        }
    }
}
