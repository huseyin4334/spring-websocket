package org.example.javanetwork.server.serversocket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Waiting for client connection...");

            while (true) {
                try (Socket client = serverSocket.accept()) {
                    System.out.println("Client connected!");
                    System.out.println(client.getInetAddress());

                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(client.getInputStream())
                    );

                    PrintWriter writer = new PrintWriter(client.getOutputStream(), true);

                    while (true) {
                        String line = reader.readLine();

                        if (line.equals("exit")) {
                            writer.println("Goodbye!");
                            break;
                        } else
                            writer.println("Server received the request: " + line);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/*
    InputStream uses byte streams to read data, while Reader uses character streams to read data.
    InputStream gets the bytes from the specified resource, while Reader reads the characters from the specified resource.

    In our case client.getInputStream() returns an InputStream object, which is used to read bytes from the client.
    To read characters from the client, we need to wrap the InputStream object in an InputStreamReader object.

    BufferedReader is used to read text from a character-based input stream. It can be used to read data line by line by readLine() method.

    PrintWriter uses character streams to send data to the client. It is used to write text to a character-based output stream.
 */
