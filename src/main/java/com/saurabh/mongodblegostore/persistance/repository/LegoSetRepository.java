package com.saurabh.mongodblegostore.persistance.repository;

import com.saurabh.mongodblegostore.model.LegoSet;
import com.saurabh.mongodblegostore.model.constants.LegoSetDifficulty;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface LegoSetRepository extends MongoRepository<LegoSet, String>, QuerydslPredicateExecutor<LegoSet> {
    /**
     * @param theme
     * @param sort  only works for declarative named queries , in imperative @Query ones, we need to add query itself to sort
     * @return
     */
    List<LegoSet> findAllByThemeContains(String theme, Sort sort);

    List<LegoSet> findAllByDifficultyAndNameStartsWith(LegoSetDifficulty difficulty, String name);

    Collection<LegoSet> findAllBy(TextCriteria textCriteria);

    //?0 = first arg
    @Query("{'delivery.deliveryFee' : {$lt : ?0}}")
    Collection<LegoSet> findAllByDeliveryPriceLessThan(int price);

    @Query("{'reviews.rating' : {$eq : 10}}")
    Collection<LegoSet> findAllByGreatReviews();

    @Query("{'paymentOptions.id' : ?0}")
    Collection<LegoSet> findByPaymentOptionId(String id);

    List<LegoSet> findAllByThemeNotLike(String excludingTheme);

    @Query("{ 'delivery.inStock' : { $eq : true } }")
    List<LegoSet> findAllByDeliveryInStock(Sort sort);
}
