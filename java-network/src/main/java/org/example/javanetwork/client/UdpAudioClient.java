package org.example.javanetwork.client;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UdpAudioClient {

    private static final int PORT = 9999;
    private static final int PACKET_SIZE = 1024;
    public static void main(String[] args) {

        try (DatagramSocket clientSocket = new DatagramSocket()) {
            byte[] fileName = "AudioClip.wav".getBytes();
            DatagramPacket sendingPackage = new DatagramPacket(fileName, fileName.length, InetAddress.getLocalHost(), PORT);
            clientSocket.send(sendingPackage);
            System.out.println("Requested file: " + new String(sendingPackage.getData()));

            System.out.println("Playing audio...");
            playAudio(clientSocket);
            System.out.println("Audio played successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void playAudio(DatagramSocket socket) throws SocketException {
        socket.setSoTimeout(1000);

        AudioFormat format = new AudioFormat(22050, 16, 1, true, false);

        DataLine.Info speakerInfo = new DataLine.Info(SourceDataLine.class, format);

        try (SourceDataLine speaker = (SourceDataLine) AudioSystem.getLine(speakerInfo)) {
            speaker.open(format);
            speaker.start();

            byte[] buffer = new byte[PACKET_SIZE];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            while (true) {
                try {
                    socket.receive(packet);
                    speaker.write(packet.getData(), 0, packet.getLength());
                } catch (IOException e) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
