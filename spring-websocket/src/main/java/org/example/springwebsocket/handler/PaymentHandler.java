package org.example.springwebsocket.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springwebsocket.model.request.PaymentRequest;
import org.example.springwebsocket.model.response.PaymentResponse;
import org.example.springwebsocket.service.ExternalService;
import org.example.springwebsocket.service.PaymentService;
import org.example.springwebsocket.utils.KafkaConstants;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.security.Principal;

@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(topics = KafkaConstants.PAYMENT_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
public class PaymentHandler {

    private final PaymentService paymentService;

    @KafkaHandler
    public void responseHandler(@Payload PaymentResponse response) {
        log.info("Received payment response: {}", response);

        paymentService.savePaymentResponse(response);
        paymentService.publishPaymentResponse(response);
    }

}
