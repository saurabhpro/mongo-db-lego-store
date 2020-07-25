package com.saurabh.mongodblegostore.controller;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.saurabh.mongodblegostore.model.LegoSet;
import com.saurabh.mongodblegostore.model.QLegoSet;
import com.saurabh.mongodblegostore.model.constants.LegoSetDifficulty;
import com.saurabh.mongodblegostore.persistance.repository.LegoSetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/legoStore/filter")
public class CustomFilteringController {

    private final LegoSetRepository legoSetRepository;

    public CustomFilteringController(LegoSetRepository legoSetRepository) {
        this.legoSetRepository = legoSetRepository;
    }

    @GetMapping("/theme/{theme}")
    public ResponseEntity<List<LegoSet>> getAllByTheme(@PathVariable String theme) {
        Sort sortByThemeAsc = Sort.by("theme").ascending();
        final List<LegoSet> legoSetList = this.legoSetRepository.findAllByThemeContains(theme, sortByThemeAsc);
        return getListResponseEntity(legoSetList);
    }

    @GetMapping("/hardWithNameStartWithM")
    public ResponseEntity<List<LegoSet>> getAllByTheme() {
        final List<LegoSet> legoSetList = this.legoSetRepository.findAllByDifficultyAndNameStartsWith(LegoSetDifficulty.HARD, "M");
        return getListResponseEntity(legoSetList);
    }

    @GetMapping("/deliveryFee/{fee}")
    public ResponseEntity<Collection<LegoSet>> getByDeliveryFee(@PathVariable int fee) {
        final Collection<LegoSet> legoSetList = this.legoSetRepository.findAllByDeliveryPriceLessThan(fee);
        return legoSetList.isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(legoSetList);
    }

    @GetMapping("/greatReviews")
    public ResponseEntity<Collection<LegoSet>> byGreatReviews() {
        final Collection<LegoSet> legoSetList = this.legoSetRepository.findAllByGreatReviews();
        return legoSetList.isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(legoSetList);
    }

    @GetMapping("/paymentOption/{id}")
    public ResponseEntity<Collection<LegoSet>> byPaymentOption(@PathVariable String id) {
        final Collection<LegoSet> legoSetList = this.legoSetRepository.findByPaymentOptionId(id);
        return legoSetList.isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(legoSetList);

    }

    @GetMapping("/themeNotLikeStarWars")
    public ResponseEntity<Collection<LegoSet>> themeNotLikeStarWars() {
        final Collection<LegoSet> legoSetList = this.legoSetRepository.findAllByThemeNotLike("Star Wars");
        return legoSetList.isEmpty() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(legoSetList);
    }

    @GetMapping("/inStock")
    public ResponseEntity<List<LegoSet>> inStock() {
        Sort sort = Sort.by(Sort.Direction.ASC, "difficulty");
        final List<LegoSet> legoSetList = this.legoSetRepository.findAllByDeliveryInStock(sort);
        return getListResponseEntity(legoSetList);

    }

    @GetMapping("bestBuys")
    public ResponseEntity<Iterable<LegoSet>> bestBuys() {
        // build query
        QLegoSet query = new QLegoSet("query");
        BooleanExpression inStockFilter = query.deliveryInfo.inStock.isTrue();
        Predicate smallDeliveryFeeFilter = query.deliveryInfo.deliveryFee.lt(50);
        Predicate hasGreatReviews = query.reviews.any().rating.eq(10);

        Predicate bestBuysFilter = inStockFilter
                .and(smallDeliveryFeeFilter)
                .and(hasGreatReviews);

        // pass the query to findAll()
        final Iterable<LegoSet> legoSets = this.legoSetRepository.findAll(bestBuysFilter);

        return legoSets.iterator().hasNext() ?
                ResponseEntity.notFound().build() :
                ResponseEntity.ok(legoSets);
    }

    @GetMapping("/fullTextSearch/{text}")
    public Collection<LegoSet> fullTextSearch(@PathVariable String text){
        TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matching(text);
        return this.legoSetRepository.findAllBy(textCriteria);
    }

    private ResponseEntity<List<LegoSet>> getListResponseEntity(List<LegoSet> legoSetList) {
        if (legoSetList == null || legoSetList.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(legoSetList);
        }
    }

}
