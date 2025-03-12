package org.example.javawebsocket.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.util.Scanner;
import java.util.concurrent.CompletionStage;

public class WebSocketClient {
    public static void main(String[] args) throws URISyntaxException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        HttpClient client = HttpClient.newHttpClient();
        WebSocket webSocket = client.newWebSocketBuilder()
                .buildAsync(
                        new URI("ws://localhost:8080?name=" + name),
                        new WebSocketListener()
                )
                // wait for the connection to be established. (Handshake)
                .join();

        while (true) {
            String message = scanner.nextLine();
            if (message.equals("exit")) {
                webSocket.sendClose(WebSocket.NORMAL_CLOSURE, "Goodbye!").join();
                break;
            }

            // last parameter uses for the final frame
            // for example, we have a big message, and we want to send it in chunks
            // we can set the last parameter to false, and the server will know that the message is not complete
            webSocket.sendText(message, true);
        }

    }
}

class WebSocketListener implements WebSocket.Listener {
    @Override
    public void onOpen(WebSocket webSocket) {
        System.out.println("WebSocket opened");
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        System.out.println("Received message: " + data);
        return null;
    }

    @Override
    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        System.out.println("WebSocket closed");
        return null;
    }

    @Override
    public void onError(WebSocket webSocket, Throwable error) {
        System.out.println("Error: " + error.getMessage());
    }
}
