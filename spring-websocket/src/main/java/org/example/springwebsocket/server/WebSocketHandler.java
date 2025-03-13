package org.example.springwebsocket.server;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.springwebsocket.service.ExternalService;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Log4j2
public class WebSocketHandler extends TextWebSocketHandler {

    private final ExternalService externalService;

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) {
        log.info("New connection: {}", session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        externalService.process(message.getPayload()).whenComplete((response, throwable) -> {
            IntStream.range(0, 5).forEach(i -> {
                try {
                    log.info("Sending message: {} to {}", response, session.getId());
                    session.sendMessage(new TextMessage(response));
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.error("Error while sleeping: {}", e.getMessage());
                } catch (IOException e) {
                    log.error("Error while sending message: {}", e.getMessage());
                }
            });
        });
        log.info("Received message: {} from {}", message.getPayload(), session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, @NonNull CloseStatus status) {
        log.info("Connection closed: {}, status is {}", session.getId(), status);
    }
}
