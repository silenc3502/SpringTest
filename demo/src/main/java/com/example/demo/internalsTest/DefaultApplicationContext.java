package com.example.demo.internalsTest;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DefaultApplicationContext implements ApplicationContext {

    final private Map<String, Object> registeredBeanMap = new HashMap<>();
    final private Set<Class<?>> candidates;

    public DefaultApplicationContext() {
        final Reflections reflections = new Reflections(
                //requiredType.getPackageName(),
                "com.example.demo",
                Scanners.TypesAnnotated);

        this.candidates = reflections.getTypesAnnotatedWith(Component.class);
        System.out.println("Constructor candidate: " + candidates);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeanException {

        final Object beanObjectByName = registeredBeanMap.get(name);

        if (beanObjectByName != null) {
            return requiredType.cast(beanObjectByName);
        }

        System.out.println("this.getClass(): " + this.getClass().getPackageName());
        System.out.println("name.getClass(): " + name.getClass().getPackageName());
        System.out.println("requiredType.getClass(): " + requiredType.getClass().getPackageName());
        System.out.println("requiredType(): " + requiredType.getPackageName());

        final String path = this.getClass().getPackageName();
        final String parentPath = path.substring(1 + path.lastIndexOf("."));
        System.out.println("parentPath: " + parentPath);

        final Class<?> foundPresetCandidate = findPresetCandidate(requiredType);
        if (foundPresetCandidate == null) {
            return null;
        }

        final Constructor<?> foundPresetCandidateConstructor = foundPresetCandidate.getConstructors()[0];
        final Parameter[] parameters = foundPresetCandidateConstructor.getParameters();
        final ArrayList<Object> beanList = new ArrayList<>(parameters.length);

        for (Parameter parameter: parameters) {
            System.out.println("parameter: " + parameter);
            System.out.println("parameter type: " + parameter.getType());

            final String beanName = generateCamelCaseBeanName(parameter.getType());
            System.out.println("beanName: " + beanName);
            final Object bean = getBean(beanName, parameter.getType());
            System.out.println("get bean: " + bean);

            beanList.add(bean);
        }

        try {
            final Object newInstance = foundPresetCandidateConstructor.newInstance(beanList.toArray());
            return requiredType.cast(newInstance);
        } catch (Exception e) {
            throw new BeanException("Bean 생성 실패!", e);
        }
    }

    private <T> Class<?> findPresetCandidate(Class<T> requiredType) {
        for (Class<?> candidate: candidates) {
            System.out.println("candidate: " + candidate);
            System.out.println("candidate.getSimpleName(): " + candidate.getSimpleName());

            if (candidate.equals(requiredType)) {
                System.out.println("It's matching!");
                return candidate;
            }
        }
        return null;
    }

    private String generateCamelCaseBeanName(Class<?> classType) {
        final String simpleName = classType.getSimpleName();
        final String firstString = simpleName.substring(0, 1);
        final String remainString = simpleName.substring(1);

        return firstString.toLowerCase() + remainString;
    }
}
