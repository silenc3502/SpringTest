package com.example.demo.internalTest;

import com.example.demo.internalsTest.Component;

@Component
public class TestController {

    final private TestService testService;

    public TestController(TestServiceImpl testServiceImpl) {
        this.testService = testServiceImpl;
    }

    public void helloTest () {
        System.out.println("helloTest()");
    }

    public String getHelloString () {
        String result = testService.helloService();
        return result;
    }
}
