package com.saurabh.mongodblegostore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.index.TextIndexed;

@Data
@AllArgsConstructor
public class ProductReview {
    // allow this name to be indexed ad searched using full text search
    @TextIndexed
    private String userName;

    private int rating;
}