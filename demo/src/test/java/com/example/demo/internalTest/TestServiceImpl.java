package com.example.demo.internalTest;

import com.example.demo.internalsTest.Component;

@Component
public class TestServiceImpl implements TestService {

    final private Integer data;

    public TestServiceImpl() {
        this.data = 3;
    }

    public TestServiceImpl(Integer data) {
        this.data = data;
    }

    @Override
    public String helloService() {
        return "Hello Test Service";
    }
}
