package com.saurabh.mongodblegostore.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductReview {
    private String userName;
    private int rating;
}