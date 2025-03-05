package org.example.javanetwork.sockets.server.nio.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class ChannelSelectorServer {
    public static void main(String[] args) {
        try (ServerSocketChannel ssc = ServerSocketChannel.open()) {
            ssc.bind(new InetSocketAddress("localhost", 8080));
            ssc.configureBlocking(false);
            System.out.println("Server bound to port " + ssc.socket().getLocalPort());

            try(Selector selector = Selector.open()) {
                ssc.register(selector, SelectionKey.OP_ACCEPT);
                while (true) {
                    System.out.println("Waiting for event to occur...");
                    selector.select(); // this code is blocking, it will return when at least one of the registered events occurs
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();

                        if (!key.isValid())
                            continue;

                        if (key.isAcceptable()) {
                            // a connection was accepted by a ServerSocketChannel
                            // this means a client tried to connect to the server
                            // we can accept the connection and get a SocketChannel
                            // to communicate with the client

                            SocketChannel client = ssc.accept();
                            System.out.println("Client connected from: " + client.getRemoteAddress());
                            client.configureBlocking(false);
                            client.register(selector, SelectionKey.OP_READ); // I sent an event to selector to read data from the client
                        } else if (key.isReadable()) {
                            // a channel is ready for reading
                            doOperation(key);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void doOperation(SelectionKey key) throws IOException {
        System.out.println("Reading data from client...");
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        // read data from the client
        // if the client closes the connection, read() will return -1
        // if the client sends data, read() will return the number of bytes read
        // if the client sends no data, read() will return 0
        int readByte = client.read(buffer);

        if (readByte > 0) {
            buffer.flip();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            String message = new String(bytes);
            System.out.println("Received from client: " + message);
            if (message.trim().equals("exit")) {
                client.write(ByteBuffer.wrap(("Good bye from server: " + message).getBytes()));
            }

            else
                client.write(ByteBuffer.wrap(("Hello from server: " + message).getBytes()));
        } else if (readByte == -1) {
            System.out.println("Connection closed by client");
            client.close();
            key.cancel(); // I remove the key from the selector
        }

    }
}
