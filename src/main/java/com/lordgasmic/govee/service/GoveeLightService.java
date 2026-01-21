package com.lordgasmic.govee.service;

import com.google.gson.Gson;
import com.lordgasmic.govee.models.GoveeLightRequest;
import com.lordgasmic.govee.models.GoveeLightResponse;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class GoveeLightService {

    @Value("${govee.lights.lordgasmic}")
    private List<String> addresses;

    private final Map<String, Boolean> statuses;

    private final Gson gson;

    public GoveeLightService() {
        statuses = new HashMap<>();
        gson = new Gson();
    }

    @PostConstruct
    public void getCurrentLampStatus() {
        final List<GoveeLightResponse> responses = addresses.stream().peek(ip -> log.info("IP: {}", ip)).map(ip -> connectToLight(ip, buildStatus())).toList();
    }

    public GoveeLightResponse connectToLight(final String ip, final GoveeLightRequest request) {
        try (final DatagramSocket clientSocket = new DatagramSocket()) {
            final InetAddress IPAddress = InetAddress.getByName(ip);
            final int port = 4003;

            final String message = gson.toJson(request);
            final byte[] sendData = message.getBytes();

            // Create packet with data, destination address, and port
            final DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            clientSocket.send(sendPacket);

            // Optional: Receive a response from the server
            final byte[] receiveData = new byte[1024];
            final DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);

            final String modifiedSentence = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Response from Server: " + modifiedSentence);

        } catch (final IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static GoveeLightRequest buildStatus() {
        return GoveeLightRequest.builder().msg(GoveeLightRequest.GoveeLightMessage.builder().cmd("devStatus").data(GoveeLightRequest.GoveeLightMessage.GoveeLightData.builder().build()).build()).build();
    }

}
