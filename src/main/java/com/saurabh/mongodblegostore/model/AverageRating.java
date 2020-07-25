package com.saurabh.mongodblegostore.model;

import lombok.Data;

@Data
public class AverageRating {
    private String id;
    private String productName;
    private double avgRating;
}
