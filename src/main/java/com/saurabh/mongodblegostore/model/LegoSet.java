package com.saurabh.mongodblegostore.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class LegoSet {

    private String id;
    private String name;
    private LegoSetDifficulty difficulty;
    private String theme;
    private Collection<ProductReview> reviews = new ArrayList<>();

    private DeliveryInfo deliveryInfo;

    private PaymentOptions paymentOptions;
}