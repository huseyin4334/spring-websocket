package org.example.javanetwork.server.nio.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class SimpleServerChannel {
    public static void main(String[] args) {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            System.out.println("Server only opened, not bound to any port");
            serverSocketChannel.bind(new InetSocketAddress("localhost", 8080));
            System.out.println("Server bound to port " + serverSocketChannel.socket().getLocalPort());

            // ServerSocketChannel is a selectable channel for stream-oriented listening sockets.
            while (true) {
                System.out.println("Waiting for client connection...");
                SocketChannel client = serverSocketChannel.accept(); // This is our channel to communicate with the client

                System.out.println("Client connected from: " + client.getRemoteAddress());

                ByteBuffer buffer = ByteBuffer.allocate(1024); // 1KB. Buffer is used to read data from the client
                int readBytes = client.read(buffer); // here is blocking call, it will wait until the client sends some data


                if (readBytes > 0) {
                    System.out.println("Received from client: " + new String(buffer.array(), StandardCharsets.UTF_8));
                    buffer.flip();
                    doOperation(buffer);
                    client.write(ByteBuffer.wrap("Hello from server: ".getBytes()));
                    buffer.clear();
                    doOperation(buffer);
                    while (buffer.hasRemaining()) {
                        System.out.println("Go back to client...");
                        client.write(buffer);
                    }
                } else if (readBytes == -1) {
                    System.out.printf("Connection to %s was closed%n", client.getRemoteAddress());
                    client.close();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void doOperation(ByteBuffer buffer) {
        System.out.printf(
                "Position: %d, Limit: %d, Capacity: %d, Remaining: %d\n",
                buffer.position(), buffer.limit(), buffer.capacity(), buffer.remaining()
        );
    }
}
