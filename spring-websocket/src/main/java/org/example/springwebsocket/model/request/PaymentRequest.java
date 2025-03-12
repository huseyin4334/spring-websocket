package org.example.springwebsocket.model.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Setter
@Getter
public class PaymentRequest implements Serializable {
    private UUID callId;
    private String userCode;
    private String productCode;
    private int qty;

    @Override
    public String toString() {
        return "PaymentRequest{" +
                "callId=" + callId +
                ", userCode='" + userCode + '\'' +
                ", productCode='" + productCode + '\'' +
                ", qty=" + qty +
                '}';
    }
}
