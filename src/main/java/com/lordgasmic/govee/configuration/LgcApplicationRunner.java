package com.lordgasmic.govee.configuration;

import com.lordgasmic.govee.service.GoveeLightService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class LgcApplicationRunner implements ApplicationRunner {

    private final GoveeLightService service;

    public LgcApplicationRunner(final GoveeLightService service) {
        this.service = service;
    }


    @Override
    public void run(final ApplicationArguments args) throws Exception {
        service.connectToLight(GoveeLightService.buildStatus());
    }
}
