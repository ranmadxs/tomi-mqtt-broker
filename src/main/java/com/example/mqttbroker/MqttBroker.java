package com.example.mqttbroker;

import io.moquette.broker.Server;
import io.moquette.broker.config.MemoryConfig;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Properties;

public class MqttBroker {
    public static void main(String[] args) {
        Server mqttBroker = new Server();
        Properties configProps = new Properties();
        configProps.setProperty("port", "80");
        configProps.setProperty("host", "0.0.0.0"); // Escucha en todas las interfaces

        try {
            mqttBroker.startServer(new MemoryConfig(configProps));

            // Obtiene la IP de la red local
            String localIpAddress = getLocalIpAddress();
            String port = configProps.getProperty("port");
            System.out.println("Servidor MQTT iniciado en la IP " + localIpAddress + " y puerto " + port);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Apagando el servidor MQTT...");
                mqttBroker.stopServer();
                System.out.println("Servidor MQTT apagado.");
            }));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getLocalIpAddress() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress.isSiteLocalAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            System.err.println("Error obteniendo la IP de la red: " + e.getMessage());
        }
        return "IP no disponible";
    }
}
