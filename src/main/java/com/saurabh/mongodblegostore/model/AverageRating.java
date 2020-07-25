package com.saurabh.mongodblegostore.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AverageRating {
    private String id;
    private String productName;
    private double avgRating;
}
