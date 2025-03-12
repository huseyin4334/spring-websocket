package org.example.springwebsocket.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Slf4j
@Component
public class SocketSessionListener {

    @EventListener
    private void handleSessionConnected(SessionSubscribeEvent event) {
        try {
            log.info("Subscribed Username: {}", event.getUser().getName());
        } catch (Exception e) {
            log.info("User not found");
        }
    }
}
