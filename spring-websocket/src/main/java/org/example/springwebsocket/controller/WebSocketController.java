package org.example.springwebsocket.controller;

import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.example.springwebsocket.model.request.PaymentRequest;
import org.example.springwebsocket.service.PaymentService;
import org.example.springwebsocket.utils.StompConstants;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Log4j2
@Controller
public class WebSocketController {

    private final PaymentService paymentService;

    // Catch the request and call the external system service.
    @MessageMapping(StompConstants.PAYMENT_REQUEST_TOPIC)
    public void catchRequest(@Payload PaymentRequest request) {
        log.info("Request received.");
        log.info(request.toString());

        // Call the external system service.
        paymentService.callPaymentService(request);
    }
}
