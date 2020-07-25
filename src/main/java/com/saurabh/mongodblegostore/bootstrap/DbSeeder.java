package com.saurabh.mongodblegostore.bootstrap;

import com.saurabh.mongodblegostore.model.DeliveryInfo;
import com.saurabh.mongodblegostore.model.LegoSet;
import com.saurabh.mongodblegostore.model.PaymentOptions;
import com.saurabh.mongodblegostore.model.ProductReview;
import com.saurabh.mongodblegostore.model.constants.LegoSetDifficulty;
import com.saurabh.mongodblegostore.model.constants.PaymentType;
import com.saurabh.mongodblegostore.persistance.repository.LegoSetRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DbSeeder implements CommandLineRunner {
    private final LegoSetRepository legoSetRepository;
    private final MongoTemplate mongoTemplate;

    public DbSeeder(LegoSetRepository legoSetRepository, MongoTemplate mongoTemplate) {
        this.legoSetRepository = legoSetRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void run(String... args) {
        // delete all entries
        this.legoSetRepository.deleteAll();

        //drop our collection and start fresh
        this.mongoTemplate.dropCollection(PaymentOptions.class);

        /*
        Payment Options
         */

        PaymentOptions creditCardPayment = new PaymentOptions(PaymentType.CreditCard, 0);
        this.mongoTemplate.insert(creditCardPayment);
        PaymentOptions payPalPayment = new PaymentOptions(PaymentType.PayPal, 1);
        this.mongoTemplate.insert(payPalPayment);
        PaymentOptions cashPayment = new PaymentOptions(PaymentType.Cash, 10);
        this.mongoTemplate.insert(cashPayment);

        /*
        Lego Sets
         */

        LegoSet millenniumFalcon = new LegoSet(
                "Millennium Falcon",
                LegoSetDifficulty.HARD,
                "Star Wars",
                List.of(
                        new ProductReview("Dan", 7),
                        new ProductReview("Anna", 10),
                        new ProductReview("John", 8)
                ),
                new DeliveryInfo(LocalDate.now().plusDays(1), 30, true),
                creditCardPayment);

        LegoSet skyPolice = new LegoSet(
                "Sky Police Air Base",
                LegoSetDifficulty.MEDIUM,
                "City",
                List.of(
                        new ProductReview("Dan", 5),
                        new ProductReview("Andrew", 8)
                ),
                new DeliveryInfo(LocalDate.now().plusDays(3), 50, true),
                creditCardPayment);

        LegoSet mcLarenSenna = new LegoSet(
                "McLaren Senna", LegoSetDifficulty.EASY,
                "Speed Champions",
                List.of(
                        new ProductReview("Bogdan", 9),
                        new ProductReview("Christa", 9)
                ),
                new DeliveryInfo(LocalDate.now().plusDays(7), 70, false),
                payPalPayment);

        LegoSet mindstormsEve = new LegoSet(
                "MINDSTORMS EV3", LegoSetDifficulty.HARD,
                "Mindstorms",
                List.of(
                        new ProductReview("Cosmin", 10),
                        new ProductReview("Jane", 9),
                        new ProductReview("James", 10)
                ),
                new DeliveryInfo(LocalDate.now().plusDays(10), 100, false),
                cashPayment);

        /**
         * Array.asList creates mutable list collection,
         * List.of creates immutable (preferred)
         */
        List<LegoSet> initialProducts = List.of(millenniumFalcon, mindstormsEve, mcLarenSenna, skyPolice);

        this.legoSetRepository.insert(initialProducts);
    }
}
