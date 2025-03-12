package org.example.springwebsocket.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PaymentTerm implements Serializable {
    private String termCode;
    private String termName;
    private String amount;
    private String currency;

    @Override
    public String toString() {
        return "PaymentTerm{" +
                "termCode='" + termCode + '\'' +
                ", termName='" + termName + '\'' +
                ", amount='" + amount + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }
}
