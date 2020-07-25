package com.saurabh.mongodblegostore.model;

import com.saurabh.mongodblegostore.model.constants.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentOptions {

    private String id;
    private PaymentType type;
    private int fee;

    public PaymentOptions(PaymentType type, int fee) {
        this.type = type;
        this.fee = fee;
    }
}
