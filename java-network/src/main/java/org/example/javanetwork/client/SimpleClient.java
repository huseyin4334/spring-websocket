package org.example.javanetwork.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SimpleClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", (args.length > 0 && args[0] != null) ? Integer.parseInt(args[0]) : 8080)) {
            System.out.println("Connected to server!");

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            int count = 1;
            do {
                System.out.println("Sending message to server... Message Count is " + count);
                writer.println("Message " + count); // This is a request to the server.
                String response = reader.readLine(); // This is the response from the server.
                System.out.println("Response: " + response);
                Thread.sleep(1000L * count); // this can max be 7 seconds
            } while (++count < 7);

            writer.println("exit");
            System.out.println(reader.readLine());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Client closed!");
        }
    }
}
