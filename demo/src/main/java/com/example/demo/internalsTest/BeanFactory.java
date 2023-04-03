package com.example.demo.internalsTest;

import org.springframework.beans.BeansException;

public interface BeanFactory {
    public <T> T getBean(String name, Class<T> requiredType);
}
