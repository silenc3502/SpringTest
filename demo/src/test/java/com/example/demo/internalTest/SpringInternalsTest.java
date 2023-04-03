package com.example.demo.internalTest;

import com.example.demo.internalsTest.DefaultApplicationContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

public class SpringInternalsTest {

    @DisplayName("ApplicationContext Test")
    @Test
    public void ApplicationContext_getBean_테스트하기 () {
        System.out.println(this.getClass().getPackageName());

        final DefaultApplicationContext sut = new DefaultApplicationContext();
        final TestController result = sut.getBean("testController", TestController.class);
        System.out.println(sut.getBean("testController", TestController.class));

        assertNotNull(result);
        assertInstanceOf(TestController.class, result);
        System.out.println(result.getHelloString());
    }

    @DisplayName("객체 불러보기")
    @Test
    public void test () throws InvocationTargetException, InstantiationException, IllegalAccessException {
        final Constructor<?>[] testServiceImplConstructors = TestServiceImpl.class.getConstructors();
        for (Constructor<?> constructor: testServiceImplConstructors) {
            System.out.println("constructor = " + constructor);
            System.out.println("constructor = " + constructor.getParameters().length);
            System.out.println("constructor = " + constructor.getParameters().toString());
        }

        final Constructor<?> defaultConstructor = testServiceImplConstructors[0];
        final Object newTestServiceInstance = defaultConstructor.newInstance(null);
        System.out.println(newTestServiceInstance);
        System.out.println(newTestServiceInstance instanceof TestServiceImpl);

        final Constructor<?>[] testControllerConstructos = TestController.class.getConstructors();
        for (Constructor<?> constructor: testControllerConstructos) {
            System.out.println("testController constructor: " + constructor);
        }

        final Constructor<?> testControllerConstruct = testControllerConstructos[0];
        final Object newTestControllerInstance = testControllerConstruct.newInstance(newTestServiceInstance);

        System.out.println(newTestControllerInstance);
        System.out.println(newTestControllerInstance instanceof TestController);
        System.out.println(newTestControllerInstance instanceof TestServiceImpl);

        final TestController castedTestControllerInstance = (TestController) newTestControllerInstance;
        System.out.println(castedTestControllerInstance.getHelloString());

        final TestController castedTestControllerInstance2 = TestController.class.cast(newTestControllerInstance);
        System.out.println(castedTestControllerInstance2.getHelloString());
    }
}
