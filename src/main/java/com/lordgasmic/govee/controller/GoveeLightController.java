package com.lordgasmic.govee.controller;

import com.lordgasmic.govee.service.GoveeLightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoveeLightController {

    private final GoveeLightService service;

    public GoveeLightController(final GoveeLightService service) {
        this.service = service;
    }

    @GetMapping("/api/v1/off")
    public ResponseEntity<Void> turnOff() {

        service.connectToLight(GoveeLightService.buildControl(0));

        return ResponseEntity.accepted().build();
    }

    @GetMapping("/api/v1/on")
    public ResponseEntity<Void> turnOn() {

        service.connectToLight(GoveeLightService.buildControl(0));

        return ResponseEntity.accepted().build();
    }

    @GetMapping("/api/v1/status")
    public ResponseEntity<Void> getStatus() {

        service.connectToLight(GoveeLightService.buildStatus());

        return ResponseEntity.accepted().build();
    }
}
