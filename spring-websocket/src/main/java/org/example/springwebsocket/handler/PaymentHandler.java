package org.example.springwebsocket.handler;

import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.example.springwebsocket.model.response.PaymentResponse;
import org.example.springwebsocket.service.PaymentService;
import org.example.springwebsocket.utils.KafkaConstants;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
@KafkaListener(topics = KafkaConstants.PAYMENT_TOPIC, groupId = "${payment.consumer.group-id}")
public class PaymentHandler {

    private final PaymentService paymentService;

    @KafkaHandler
    public void responseHandler(@Payload PaymentResponse response) {
        log.info("Received payment response: {}", response);

        paymentService.savePaymentResponse(response);
        paymentService.publishPaymentResponse(response);
    }

}
