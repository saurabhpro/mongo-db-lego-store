package com.saurabh.mongodblegostore.persistance.service;

import com.saurabh.mongodblegostore.model.LegoSet;
import com.saurabh.mongodblegostore.model.projections.AvgRatingModel;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

@Service
public class ReportService {

    private MongoTemplate mongoTemplate;

    public ReportService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * This method perform aggregation projection using MongoTemplate
     *
     * @return
     */
    public List<AvgRatingModel> getAvgRatingReport() {
        /*
         * expressions define the name should become -> projectName and so on,
         * basically as the new projection model {@link AvgRatingModel}
         */
        final ProjectionOperation projectToMatchModel = project()
                .andExpression("name").as("productName")
                .andExpression("{$avg : '$reviews.rating'}").as("avgRating");

        /*
         * define new aggregation on LegoSet class using the above rules
         */
        final Aggregation avgRatingAggregation = newAggregation(LegoSet.class, projectToMatchModel);

        /*
         * call the aggregate method using our aggregation and source and destination class
         */
        return this.mongoTemplate
                .aggregate(avgRatingAggregation, LegoSet.class, AvgRatingModel.class)
                .getMappedResults();
    }
}