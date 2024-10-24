package org.krosmozClash;

import org.krosmozClash.presentation.menu.MainMenu;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainApp.class);

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        MainMenu mainMenu = context.getBean(MainMenu.class);

        org.h2.tools.Server h2WebServer = context.getBean("h2WebServer", org.h2.tools.Server.class);
        String h2ConsoleUrl = h2WebServer.getURL();
        LOGGER.info("H2 Console URL: {}", h2ConsoleUrl);

        mainMenu.afficherMenuPrincipal();
    }
}
