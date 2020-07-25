package com.saurabh.mongodblegostore.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DeliveryInfo {
    private LocalDate deliveryDate;
    private int deliveryFee;
    private boolean inStock;
}