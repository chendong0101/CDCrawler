package com.cd.test.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by chendong on 15-11-24.
 */
@Component
public class AspectTestMain {

    @AspectAnno
    public void f() {
        System.out.println("real object");
    }

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-aop.xml");
        AspectTestMain aspectTestMain = (AspectTestMain)context.getBean("aspectTestMain");
        aspectTestMain.f();
    }
}
