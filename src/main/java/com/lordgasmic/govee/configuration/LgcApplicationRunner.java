package com.lordgasmic.govee.configuration;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class LgcApplicationRunner implements ApplicationRunner {

    @Override
    public void run(final ApplicationArguments args) throws Exception {
        System.out.println("This code runs after the application context is fully initialized.");

        // Example of accessing arguments:
        for (final String optionName : args.getOptionNames()) {
            System.out.println(optionName + ": " + args.getOptionValues(optionName));
        }
    }
}
