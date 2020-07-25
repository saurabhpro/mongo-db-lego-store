package com.saurabh.mongodblegostore.persistance.repository;

import com.saurabh.mongodblegostore.model.DeliveryInfo;
import com.saurabh.mongodblegostore.model.LegoSet;
import com.saurabh.mongodblegostore.model.PaymentOptions;
import com.saurabh.mongodblegostore.model.ProductReview;
import com.saurabh.mongodblegostore.model.constants.LegoSetDifficulty;
import com.saurabh.mongodblegostore.model.constants.PaymentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class LegoSetRepositoryTest {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private LegoSetRepository legoSetRepository;

    @BeforeEach
    public void before() {
        this.legoSetRepository.deleteAll();

        LegoSet millenniumFalcon = new LegoSet(
                "Millennium Falcon",
                LegoSetDifficulty.HARD, "Star Wars",
                List.of(
                        new ProductReview("Dan", 7),
                        new ProductReview("Anna", 10),
                        new ProductReview("John", 8)
                ),
                new DeliveryInfo(LocalDate.now().plusDays(1), 30, true),

                new PaymentOptions(PaymentType.CreditCard, 0));

        LegoSet skyPolice = new LegoSet(
                "Sky Police Air Base",
                LegoSetDifficulty.MEDIUM,
                "City",
                List.of(
                        new ProductReview("Dan", 5),
                        new ProductReview("Andrew", 8)
                ), new DeliveryInfo(LocalDate.now().plusDays(3), 50, false),

                new PaymentOptions(PaymentType.CreditCard, 0));

        this.legoSetRepository.insert(millenniumFalcon);
        this.legoSetRepository.insert(skyPolice);
    }

    @Test
    public void findAllByGreatReviews_should_return_products_that_have_a_review_with_a_rating_of_ten() {
        List<LegoSet> results = (List<LegoSet>) this.legoSetRepository.findAllByGreatReviews();

        assertEquals(1, results.size());
        assertEquals("Millennium Falcon", results.get(0).getName());
    }

    /**
     * Write a new integration test that check the findAllInStock() method in the LegoSetRepository is working as expected.
     * The method uses @Query to define its behavior, so it is a good idea to test it.
     * <p>
     * Input : Add 2 products in the database, one in stock, the other out of stock
     * <p>
     * Output : 1 product
     */
    @Test
    @DisplayName("check the findAllInStock() method")
    public void findAllInStock() {
        List<LegoSet> results = this.legoSetRepository.findAllByDeliveryInStock(Sort.unsorted());

        assertEquals(1, results.size());
        assertEquals("Millennium Falcon", results.get(0).getName());
    }
}