package com.saurabh.mongodblegostore.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import java.util.Objects;

@Slf4j
@Configuration
public class Config extends AbstractMongoClientConfiguration {

    private final Environment env;

    @Value("${spring.data.mongodb.database}")
    private String database;

    public Config(Environment env) {
        this.env = env;
    }

    @Override
    protected String getDatabaseName() {
        log.info(database);
        return database;
    }

    //important in newer versions of Spring Boot
    @Override
    protected boolean autoIndexCreation() {
        return true;
    }

    @Bean
    @Override
    public MongoDatabaseFactory mongoDbFactory() {
        return new SimpleMongoClientDatabaseFactory(Objects.requireNonNull(env.getProperty("spring.data.mongodb.uri")));
    }
}