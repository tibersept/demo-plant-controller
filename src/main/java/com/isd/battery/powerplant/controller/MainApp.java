package com.isd.battery.powerplant.controller;

import org.apache.camel.main.Main;

import com.isd.battery.powerplant.controller.beans.Console;

/**
 * A Camel Application
 */
public class MainApp {

    /**
     * A main() so we can easily run these routing rules in our IDE
     */
    public static void main(String... args) throws Exception {
        Main main = new Main();
        main.enableHangupSupport();
        main.addRouteBuilder(new ValidationRouteBuilder());
        main.bind("console", new Console());
        main.run(args);
    }

}

