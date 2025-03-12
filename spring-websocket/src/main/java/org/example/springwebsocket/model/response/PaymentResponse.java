package org.example.springwebsocket.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.springwebsocket.model.pojo.PaymentTerm;
import org.example.springwebsocket.utils.RedisCacheKeyConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

// timeToLive: 60 seconds
@RedisHash(value = RedisCacheKeyConstants.PAYMENT_RESPONSE_KEY, timeToLive = (60 * 10))
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PaymentResponse implements Serializable {
    @Id
    private UUID callId;

    @Indexed
    private String userCode;

    @Indexed
    private String productCode;

    @Indexed
    private int quantity;

    private List<PaymentTerm> paymentTerms;

    @Override
    public String toString() {
        return "PaymentResponse{" +
                "callId=" + callId +
                ", userCode='" + userCode + '\'' +
                ", productCode='" + productCode + '\'' +
                ", paymentTerms=" + paymentTerms +
                '}';
    }
}
