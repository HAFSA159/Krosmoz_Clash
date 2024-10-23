package org.krosmozclash;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("com/esports/config/applicationContext.xml");

        // Your logic to use the beans from the context
    }
}
