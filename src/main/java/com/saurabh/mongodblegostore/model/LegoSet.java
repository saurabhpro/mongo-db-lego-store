package com.saurabh.mongodblegostore.model;

import com.saurabh.mongodblegostore.model.constants.LegoSetDifficulty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Collection;

@Data
@Document(collection = "LegoSet")
public class LegoSet {

    @Transient
    int noOfParts;

    @Id
    private String id;

    @TextIndexed
    private String name;

    private LegoSetDifficulty difficulty;

    @TextIndexed
    @Indexed(direction = IndexDirection.ASCENDING)
    private String theme;

    private Collection<ProductReview> reviews = new ArrayList<>();

    @Field("delivery")
    private DeliveryInfo deliveryInfo;

    private PaymentOptions paymentOptions;

    // @PersistenceConstructor if we had more constructors
    public LegoSet(String name, LegoSetDifficulty difficulty, String theme, Collection<ProductReview> reviews, DeliveryInfo deliveryInfo, PaymentOptions paymentOptions) {
        this.name = name;
        this.difficulty = difficulty;
        this.theme = theme;
        if (reviews != null) {
            this.reviews = reviews;
        }
        this.deliveryInfo = deliveryInfo;
        this.paymentOptions = paymentOptions;
    }
}