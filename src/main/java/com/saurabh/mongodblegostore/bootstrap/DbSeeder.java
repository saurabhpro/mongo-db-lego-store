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
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;

@Service
public class DbSeeder implements CommandLineRunner {
    private LegoSetRepository legoSetRepository;
    private MongoTemplate mongoTemplate;

    public DbSeeder(LegoSetRepository legoSetRepository, MongoTemplate mongoTemplate) {
        this.legoSetRepository = legoSetRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void run(String... args) {
        this.legoSetRepository.deleteAll();
        this.mongoTemplate.dropCollection(PaymentOptions.class);

        /*
        Payment Options
         */

        PaymentOptions creditCardPayment = new PaymentOptions(PaymentType.CreditCard, 0);
        PaymentOptions payPalPayment = new PaymentOptions(PaymentType.PayPal, 1);
        PaymentOptions cashPayment = new PaymentOptions(PaymentType.Cash, 10);
        this.mongoTemplate.insert(creditCardPayment);
        this.mongoTemplate.insert(payPalPayment);
        this.mongoTemplate.insert(cashPayment);

        /*
        Lego Sets
         */

        LegoSet millenniumFalcon = new LegoSet(
                "Millennium Falcon",
                LegoSetDifficulty.HARD, "Star Wars",

                Arrays.asList(
                        new ProductReview("Dan", 7),
                        new ProductReview("Anna", 10),
                        new ProductReview("John", 8)
                ), new DeliveryInfo(LocalDate.now().plusDays(1), 30, true),

                creditCardPayment);

        LegoSet skyPolice = new LegoSet(
                "Sky Police Air Base",
                LegoSetDifficulty.MEDIUM,
                "City",
                Arrays.asList(
                        new ProductReview("Dan", 5),
                        new ProductReview("Andrew", 8)
                ), new DeliveryInfo(LocalDate.now().plusDays(3), 50, true),

                creditCardPayment);

        LegoSet mcLarenSenna = new LegoSet(
                "McLaren Senna", LegoSetDifficulty.EASY,
                "Speed Champions",
                Arrays.asList(
                        new ProductReview("Bogdan", 9),
                        new ProductReview("Christa", 9)
                ),
                new DeliveryInfo(LocalDate.now().plusDays(7), 70, false),

                payPalPayment);

        LegoSet mindstormsEve = new LegoSet(
                "MINDSTORMS EV3", LegoSetDifficulty.HARD,
                "Mindstorms",
                Arrays.asList(
                        new ProductReview("Cosmin", 10),
                        new ProductReview("Jane", 9),
                        new ProductReview("James", 10)
                ),
                new DeliveryInfo(LocalDate.now().plusDays(10), 100, false),

                cashPayment);

        Collection<LegoSet> initialProducts = Arrays.asList(millenniumFalcon, mindstormsEve, mcLarenSenna, skyPolice);

        this.legoSetRepository.insert(initialProducts);
    }
}
