package org.example.springwebsocket.listener;


import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Log4j2
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
