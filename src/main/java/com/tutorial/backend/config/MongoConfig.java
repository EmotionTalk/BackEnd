package com.tutorial.backend.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.example.mongo")
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "EmotionTalk";
    }

    @Bean
    @Override
    public MongoClient mongoClient() {
        return MongoClients.create("mongodb+srv://rickyhong0130:NDhlCvIVtJW9zI49@emotiontalk.ux9e1.mongodb.net/?retryWrites=true&w=majority&appName=EmotionTalk");
    }

    @Override
    protected boolean autoIndexCreation() {
        return true;
    }
}
