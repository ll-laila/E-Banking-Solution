package com.example.user.users.repository;

import com.example.user.users.entity.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ClientRepository extends MongoRepository<Client, String> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
    @Query(value = "{ 'phoneNumber': ?0 }", fields = "{ '_id': 1 }")
    Client findIdByPhoneNumber(String phoneNumber);
}

