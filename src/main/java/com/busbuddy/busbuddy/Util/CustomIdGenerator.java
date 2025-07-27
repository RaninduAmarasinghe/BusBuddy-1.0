package com.busbuddy.busbuddy.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Random;

@Component
public class CustomIdGenerator {

    @Autowired
    private MongoTemplate mongoTemplate;

    private final Random random = new Random();

    public String generateUniqueId(String prefix, String collectionName) {
        String id;
        boolean exists;
        do {
            int randomNum = 1000 + random.nextInt(9000); // Generate 4-digit number
            id = prefix + randomNum;
            Query query = new Query(Criteria.where("_id").is(id));
            exists = mongoTemplate.exists(query, collectionName);
        } while (exists);
        return id;
    }
}