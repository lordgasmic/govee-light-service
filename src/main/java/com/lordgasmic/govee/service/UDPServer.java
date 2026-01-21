package com.lordgasmic.govee.service;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.*;

@Slf4j
public class UDPServer implements Runnable {
    private final DatagramSocket socket;
    private boolean running;
    private final byte[] buffer = new byte[1024];

    public UDPServer(final int port) {
        try {
            // Bind the socket to the specified port
            final InetAddress localhost = InetAddress.getByName("0.0.0.0");
            socket = new DatagramSocket(port);
            log.info("UDP Server started on ip: {}", socket.getInetAddress().getHostAddress());
//            log.info("UDP Server started on host: {}", socket.getInetAddress().getHostName());
            log.info("UDP Server started on port {}", port);
        } catch (final SocketException e) {
            throw new RuntimeException(e);
        } catch (final UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                log.info("listening...");
                // Create a DatagramPacket to receive data
                final DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet); // Blocks until a packet is received

                // Extract sender's information
                final InetAddress address = packet.getAddress();
                final int port = packet.getPort();

                // Process the received message
                final String received = new String(packet.getData(), 0, packet.getLength());
                log.info("Received message from {}:{} -> {}", address.getHostAddress(), port, received);

                // Simple echo logic: if message is "end", stop the server
                if (received.equals("end")) {
                    running = false;
                    continue;
                }

                // Send a response back to the client
                final String responseStr = "Ok.";
                final byte[] responseBytes = responseStr.getBytes();
                final DatagramPacket responsePacket = new DatagramPacket(responseBytes, responseBytes.length, address, port);
                socket.send(responsePacket);

            } catch (final IOException e) {
                if (running) {
                    log.error("Error when receiveing packets", e);
                }
            }
        }
        socket.close(); // Close the socket when the server stops
        log.info("UDP Server stopped.");
    }

    public void stop() {
        running = false;
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }
}
