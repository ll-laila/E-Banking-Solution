package com.example.user.users.repository;


import com.example.user.users.entity.Service;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ServiceRepository extends MongoRepository<Service, String> {

}
