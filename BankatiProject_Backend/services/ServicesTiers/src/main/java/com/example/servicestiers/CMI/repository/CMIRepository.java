package com.example.servicestiers.CMI.repository;

import com.example.servicestiers.CMI.entity.CMIEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CMIRepository extends MongoRepository<CMIEntity, Long> {
}
