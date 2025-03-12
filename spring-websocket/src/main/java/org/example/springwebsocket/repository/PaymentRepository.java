package org.example.springwebsocket.repository;

import org.example.springwebsocket.model.response.PaymentResponse;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PaymentRepository extends CrudRepository<PaymentResponse, UUID> {
    PaymentResponse findByUserCodeAndProductCodeAndQuantity(String userCode, String productCode, int quantity);
}
