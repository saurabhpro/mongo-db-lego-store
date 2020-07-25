package com.saurabh.mongodblegostore.model.projections;

import lombok.Getter;

@Getter
public class AvgRatingModel {
    private String id;
    private String productName;
    private double avgRating;
}
