package org.example.springwebsocket.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.example.springwebsocket.model.pojo.PaymentTerm;
import org.example.springwebsocket.model.request.PaymentRequest;
import org.example.springwebsocket.model.response.PaymentResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Log4j2
@RequiredArgsConstructor
@Service
public class ExternalService {

    private final RestTemplate restTemplate;

    public CompletableFuture<String> process(String message) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Processed: " + message;
        });
    }

    public void sendRequestToExternal(PaymentRequest request) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(() -> {
            try {
                log.info("Request sent to external service. After 10 seconds, the response will be received.");
                Thread.sleep(10000);

                restTemplate.postForLocation(
                        "http://localhost:8080/external/payment",
                        new PaymentResponse(
                                request.getCallId(),
                                request.getUserCode(),
                                request.getProductCode(),
                                request.getQty(),
                                List.of(
                                        new PaymentTerm(
                                                "V00",
                                                "Cash",
                                                "25.000",
                                                "USD"
                                        ),
                                        new PaymentTerm(
                                                "V01",
                                                "30 days maturity",
                                                "30.000",
                                                "USD"
                                        ),
                                        new PaymentTerm(
                                                "V02",
                                                "60 days maturity",
                                                "50.000",
                                                "USD"
                                        ),
                                        new PaymentTerm(
                                                "V03",
                                                "90 days maturity",
                                                "60.000",
                                                "USD"
                                        ),
                                        new PaymentTerm(
                                                "V04",
                                                "Credit Card",
                                                "70.000",
                                                "USD"
                                        )
                                )
                        )
                );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
