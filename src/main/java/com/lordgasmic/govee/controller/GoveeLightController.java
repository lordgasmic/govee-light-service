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

        service.connectToLight(null, null);

        return ResponseEntity.accepted().build();
    }
}
