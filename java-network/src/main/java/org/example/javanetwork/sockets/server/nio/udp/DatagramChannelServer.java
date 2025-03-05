package org.example.javanetwork.sockets.server.nio.udp;

import org.example.javanetwork.sockets.server.serversocket.udp.UdpAudioServer;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatagramChannelServer {
    private static final int PORT = 9999;
    private static final int PACKET_SIZE = 1024;
    private static final ClassLoader classLoader = UdpAudioServer.class.getClassLoader();

    public static void main(String[] args) {
        try (DatagramChannel serverChannel = DatagramChannel.open()) {
            serverChannel.bind(new InetSocketAddress(PORT));
            System.out.println("Server started on port " + PORT);

            ExecutorService executorService = Executors.newCachedThreadPool();

            System.out.println("Waiting for client to send file name...");
            try (Selector selector = Selector.open()) {
                serverChannel.configureBlocking(false); // Don't block the thread
                serverChannel.register(selector, SelectionKey.OP_READ); // Register the channel to the selector for read operation. (This means that the server will be able to read data from the client)

                ByteBuffer receiveBuffer = ByteBuffer.allocate(PACKET_SIZE);

                while (true) {
                    selector.select(); // Wait for the selected keys to be ready for I/O operation. We will wait for events on the serverChannel.

                    for (SelectionKey key : selector.selectedKeys()) {
                        if (key.isReadable()) {
                            receiveBuffer.clear();
                            DatagramChannel registeredChannel = (DatagramChannel) key.channel(); // I won't get it with try block because I won't close it.
                            SocketAddress clientAddress = registeredChannel.receive(receiveBuffer); // Read data from the client
                            receiveBuffer.flip();

                            byte[] data = new byte[receiveBuffer.remaining()];
                            receiveBuffer.get(data);
                            String fileName = new String(data);
                            System.out.println("Client requested file: " + fileName);
                            executorService.submit(() -> sendFile(fileName, clientAddress, registeredChannel));
                        }
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void sendFile(String fileName, SocketAddress clientAddress, DatagramChannel serverChannel) {
        // Implement sending file to the client

        File file = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
        System.out.println("File in server: " + file.getPath());

        try (FileChannel fileChannel = FileChannel.open(Paths.get(file.getPath()), StandardOpenOption.READ)) {
            ByteBuffer sendData = ByteBuffer.allocate(PACKET_SIZE);

            System.out.println("Sending audio file to client...");

            int size;
            do {
                sendData.clear();
                size = fileChannel.read(sendData);
                if (size > 0) {
                    sendData.flip();
                    while (sendData.hasRemaining())
                        serverChannel.send(sendData, clientAddress);
                }
            } while (size > 0);

            try {
                // This is to simulate the delay in sending the file
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("File sent successfully!");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
