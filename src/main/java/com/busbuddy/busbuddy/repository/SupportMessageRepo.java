package com.busbuddy.busbuddy.repository;

import com.busbuddy.busbuddy.model.SupportMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SupportMessageRepo extends MongoRepository<SupportMessage, String> {
}