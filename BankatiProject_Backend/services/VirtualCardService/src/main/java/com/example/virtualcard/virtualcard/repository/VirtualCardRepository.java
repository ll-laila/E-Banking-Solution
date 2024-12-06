package com.example.virtualcard.virtualcard.repository;

import com.example.virtualcard.virtualcard.entity.VirtualCard;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VirtualCardRepository extends MongoRepository<VirtualCard, String> {

}
