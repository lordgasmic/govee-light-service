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

//    // 1. Define the input channel where received packets will be sent
//    @Bean
//    public MessageChannel udpChannel() {
//        return new DirectChannel();
//    }
//
//    // 2. Configure the inbound UDP adapter
//    @Bean
//    public UnicastReceivingChannelAdapter udpInboundAdapter() {
//        final UnicastReceivingChannelAdapter adapter = new UnicastReceivingChannelAdapter(UDP_PORT);
//        adapter.setOutputChannelName("udpChannel");
//        return adapter;
//    }
//
//    // 3. Define a service activator to handle the incoming messages
//    @ServiceActivator(inputChannel = "udpChannel")
//    public void handleUdpMessage(final byte[] messagePayload) {
//        // The payload is a byte array; convert it to a string for processing
//        final String receivedMessage = new String(messagePayload).trim();
//        System.out.println("Received message: " + receivedMessage);
//        // You can add further processing logic here
//
//        GoveeLightResponse response = gson.fromJson(receivedMessage, GoveeLightResponse.class);
//
//    }

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
