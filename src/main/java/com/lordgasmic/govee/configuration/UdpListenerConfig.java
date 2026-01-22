package com.lordgasmic.govee.configuration;

import com.google.gson.Gson;
import com.lordgasmic.govee.service.LgcStatusCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.ip.dsl.Udp;

@Configuration
@EnableIntegration
@IntegrationComponentScan
public class UdpListenerConfig {

    private static final int UDP_PORT = 4002;

    private final LgcStatusCache cache;

    private final Gson gson;

    public UdpListenerConfig(final LgcStatusCache cache) {
        this.cache = cache;

        gson = new Gson();
    }

    @Bean
    public IntegrationFlow udpInboundFlow() {
        return IntegrationFlow.from(Udp.inboundAdapter(UDP_PORT))
                .handle((payload, headers) -> {
                    final String receivedMessage = new String((byte[]) payload).trim();
                    System.out.println("Received message via DSL: " + receivedMessage);
                    return null; // The adapter is for one-way communication
                })
                .get();
    }
}
