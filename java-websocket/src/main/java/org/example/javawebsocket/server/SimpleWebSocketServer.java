package org.example.javawebsocket.server;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SimpleWebSocketServer extends WebSocketServer {

    private static final Map<String, String> connectedClients = new ConcurrentHashMap<>();

    public SimpleWebSocketServer(int port) {
        super(new InetSocketAddress(port));
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        String parameters = handshake.getResourceDescriptor();
        String name = parameters.split("=")[1];

        connectedClients.put(conn.getRemoteSocketAddress().toString(), name);
        System.out.println("Connected clients: " + connectedClients.values());
        broadcastMessage(conn, name + " joined the chat.");

        System.out.println("New connection from " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        System.out.println("Closed connection to " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Message from client: " + message);

        broadcastMessage(conn, message);
    }

    private void broadcastMessage(WebSocket conn, String message) {
        List<WebSocket> connections = new ArrayList<>(getConnections());
        connections.remove(conn);
        broadcast(message, connections);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        System.out.println("Error: " + ex.getMessage());
    }

    @Override
    public void onStart() {
        System.out.println("Server started successfully on " + getPort());
    }

    public static void main(String[] args) {
        SimpleWebSocketServer server = new SimpleWebSocketServer(8080);
        server.start();
        System.out.println("WebSocket server started on port: " + server.getPort());
    }
}
