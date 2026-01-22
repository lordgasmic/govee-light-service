package com.lordgasmic.govee.service;

import com.google.gson.Gson;
import com.lordgasmic.govee.models.GoveeLightData;
import com.lordgasmic.govee.models.GoveeLightMessage;
import com.lordgasmic.govee.models.GoveeLightRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.List;

@Service
@Slf4j
public class GoveeLightService {

    private static final String DEV_STATUS = "devStatus";
    private static final String TURN = "turn";

    @Value("${govee.lights.lordgasmic}")
    private List<String> addresses;

    private final LgcStatusCache cache;

    private final Gson gson;

    public GoveeLightService(final LgcStatusCache cache) {
        this.cache = cache;

        gson = new Gson();
    }

    public void connectToLight(final GoveeLightRequest request) {
        if (!request.getMsg().getCmd().equals(DEV_STATUS)) {
            addresses.stream().peek(ip -> log.info("IP: {}", ip)).forEach(ip -> send(ip, request));
        }

        addresses.stream().peek(ip -> log.info("IP: {}", ip)).forEach(ip -> send(ip, buildStatus()));
    }

    private void send(final String ip, final GoveeLightRequest request) {
        try (final DatagramSocket clientSocket = new DatagramSocket()) {
            final InetAddress IPAddress = InetAddress.getByName(ip);
            final int port = 4003;

            final String message = gson.toJson(request);
            log.info("message to send: {}", message);
            final byte[] sendData = message.getBytes();

            // Create packet with data, destination address, and port
            final DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            clientSocket.send(sendPacket);
            log.info("sent packet");
        } catch (final IOException e) {
            log.error("Error trying to UDP", e);
        }
    }

    public boolean statusOf(final String ip) {
        final Boolean status = cache.getStatusCache().get(ip);

        return status != null && status;
    }

    public static GoveeLightRequest buildStatus() {
        return GoveeLightRequest.builder().msg(GoveeLightMessage.builder().cmd(DEV_STATUS).data(GoveeLightData.builder().build()).build()).build();
    }

    public static GoveeLightRequest buildControl(final int status) {
        return GoveeLightRequest.builder().msg(GoveeLightMessage.builder().cmd(TURN).data(GoveeLightData.builder().value(status).build()).build()).build();
    }

}
