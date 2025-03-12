package org.example.springwebsocket.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.example.springwebsocket.model.response.PaymentResponse;
import org.example.springwebsocket.utils.KafkaConstants;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/external/payment")
@RestController
public class ExternalSystemRestController {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    /*
        External system will call us from this api.
        We will produce the payment response to Kafka.
     */
    @PostMapping
    public void catchPayment(@RequestBody PaymentResponse response) {
        log.info("Payment response received: {}", response);
        kafkaTemplate.send(KafkaConstants.PAYMENT_TOPIC, response);
    }
}
