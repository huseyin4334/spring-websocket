package org.example.springwebsocket.service;

import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.example.springwebsocket.model.request.PaymentRequest;
import org.example.springwebsocket.model.response.PaymentResponse;
import org.example.springwebsocket.repository.PaymentRepository;
import org.example.springwebsocket.utils.StompConstants;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Service
public class PaymentService {

    private final ExternalService externalService;
    private final PaymentRepository paymentRepository;
    private final SimpMessagingTemplate stompTemplate;
    private final SimpUserRegistry simpUserRegistry;

    public void callPaymentService(PaymentRequest request) {
        // call payment service
        PaymentResponse response = paymentRepository.findByUserCodeAndProductCodeAndQuantity(
                request.getUserCode(),
                request.getProductCode(),
                request.getQty()
        );

        if (response == null) {
            request.setCallId(UUID.randomUUID());
            externalService.sendRequestToExternal(request);
        }
        else
            publishPaymentResponse(response);
    }

    public void savePaymentResponse(PaymentResponse response) {
        paymentRepository.save(response);
    }

    public void publishPaymentResponse(PaymentResponse response) {
        Set<SimpUser> users = simpUserRegistry.getUsers();
        log.info("Users: {}", users);

        stompTemplate.convertAndSend(
                StompConstants.PAYMENT_RESPONSE_TOPIC.formatted(response.getUserCode()),
                response
        );
    }
}
