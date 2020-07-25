package com.saurabh.mongodblegostore.model;

import lombok.Data;

@Data
public class PaymentOptions {

    private String id;
    private PaymentType type;
    private int fee;


}
