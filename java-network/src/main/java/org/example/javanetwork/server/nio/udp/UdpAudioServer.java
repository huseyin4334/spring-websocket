package org.example.javanetwork.server.nio.udp;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

public class UdpAudioServer {
    private static final int PORT = 9999;
    private static final int PACKET_SIZE = 1024;
    private static final ClassLoader classLoader = UdpAudioServer.class.getClassLoader();

    public static void main(String[] args) {
        try (DatagramSocket serverSocket = new DatagramSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            byte[] receiveData = new byte[PACKET_SIZE];

            System.out.println("Waiting for client to send file name...");
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);

            String fileName = new String(receiveData, 0, receivePacket.getLength());
            File file = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
            System.out.println("Client requested file: " + fileName);

            try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file)) {
                System.out.println("File format: " + audioInputStream.getFormat());
            } catch (UnsupportedAudioFileException e) {
                throw new RuntimeException(e);
            }

            sendAudioFile(serverSocket, receivePacket, file);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void sendAudioFile(DatagramSocket serverSocket, DatagramPacket receivePacket, File file) {
        System.out.println("File in server: " + file.getPath());
        try (FileChannel fileChannel = FileChannel.open(Paths.get(file.getPath()), StandardOpenOption.READ)) {
            ByteBuffer sendData = ByteBuffer.allocate(PACKET_SIZE);
            InetAddress clientAddress = receivePacket.getAddress();
            int clientPort = receivePacket.getPort();

            System.out.println("Sending audio file to client...");

            int size = -1;
            do {
                sendData.clear();
                size = fileChannel.read(sendData);
                if (size > 0) {
                    sendData.flip();
                    while (sendData.hasRemaining()) {
                        byte[] buffer = new byte[sendData.remaining()];
                        sendData.get(buffer);
                        DatagramPacket sendPacket = new DatagramPacket(buffer, buffer.length, clientAddress, clientPort);
                        serverSocket.send(sendPacket);
                    }
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
