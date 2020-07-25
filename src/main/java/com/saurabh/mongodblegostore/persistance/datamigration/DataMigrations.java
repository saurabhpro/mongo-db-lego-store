package com.saurabh.mongodblegostore.persistance.datamigration;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.saurabh.mongodblegostore.model.LegoSet;
import com.saurabh.mongodblegostore.model.PaymentOptions;
import com.saurabh.mongodblegostore.model.constants.PaymentType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * Like we can have a per release approach and create new classes,
 * only ChangeLog classes will be used for data migration by MongoBee
 */
@Slf4j
@ChangeLog(order = "001")
public class DataMigrations {

    /**
     * Order of change set matters , so we keep order, and if this order is present , don't update
     *
     * @param mongoTemplate injected
     */
    @ChangeSet(
            order = "001",
            author = "saurabh",
            id = "update nb parts",
            runAlways = false,
            systemVersion = "0.2"
    )
    public void updateNbParts(MongoTemplate mongoTemplate) {
        // type: org.springframework.data.mongodb.core.MongoTemplate
        // Spring Data integration allows using MongoTemplate in the ChangeSet

        final String nbParts = "nbParts";

        Criteria priceZeroCriteria = new Criteria()
                .orOperator(
                        Criteria.where(nbParts).is(0),
                        Criteria.where(nbParts).is(null)
                );

        mongoTemplate.updateMulti(
                new Query(priceZeroCriteria),
                Update.update(nbParts, 122),
                LegoSet.class
        );

        log.info("Applied change set 001");
    }

    @ChangeSet(order = "002", author = "dan", id = "insert additional payment method")
    public void insertAdditionalPaymentOption(MongoTemplate mongoTemplate) {
        PaymentOptions bankTransferPayment = new PaymentOptions(PaymentType.BankTransfer, 1);
        mongoTemplate.insert(bankTransferPayment);
    }

}
