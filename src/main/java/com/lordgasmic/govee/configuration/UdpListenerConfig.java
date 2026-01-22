package com.lordgasmic.govee.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.ip.udp.UnicastReceivingChannelAdapter;
import org.springframework.messaging.MessageChannel;

@Configuration
@EnableIntegration
@IntegrationComponentScan
public class UdpListenerConfig {

    private static final int UDP_PORT = 4002;

    // 1. Define the input channel where received packets will be sent
    @Bean
    public MessageChannel udpChannel() {
        return new DirectChannel();
    }

    // 2. Configure the inbound UDP adapter
    @Bean
    public UnicastReceivingChannelAdapter udpInboundAdapter() {
        final UnicastReceivingChannelAdapter adapter = new UnicastReceivingChannelAdapter(UDP_PORT);
        adapter.setOutputChannelName("udpChannel");
        return adapter;
    }

    // 3. Define a service activator to handle the incoming messages
    @ServiceActivator(inputChannel = "udpChannel")
    public void handleUdpMessage(final byte[] messagePayload) {
        // The payload is a byte array; convert it to a string for processing
        final String receivedMessage = new String(messagePayload).trim();
        System.out.println("Received message: " + receivedMessage);
        // You can add further processing logic here
    }
}
