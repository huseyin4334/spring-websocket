package org.example.javanetwork.server.serversocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadedSimpleServer {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Waiting for client connection...");

            while (true) {
                Socket client = serverSocket.accept();
                System.out.println("Client connected!");
                System.out.println(client.getInetAddress());
                client.setSoTimeout(8000); // if client does not send any data in 8 seconds, throw a SocketTimeoutException and close the connection

                executorService.submit(() -> socketOperation(client));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void socketOperation(Socket client) {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
        ) {
            while (true) {
                String line = reader.readLine();

                if (line.equals("exit")) {
                    System.out.println("Client disconnected!");
                    writer.println("Goodbye!");
                    client.close(); // if operation is done, close the connection
                    break;
                } else
                    writer.println("Server received the request: " + line);
            }
        } catch (IOException e) {
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


    When a client connects, a new socket is created for communication. If we execute accept in try-with-resources, the socket will be closed when the try block is exited.
    Because of that, we should change this code practice in order to keep the socket open for communication for multithreaded server.
 */
