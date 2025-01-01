package com.example.user.users.repository;

import com.example.user.users.entity.Client;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ClientRepository extends MongoRepository<Client, String> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);


    //String findIdByPhoneNumber(String phoneNumber);

    @Query(value = "{ 'phoneNumber': ?0 }", fields = "{ '_id': 1 }")
    Client findIdByPhoneNumber(String phoneNumber);
}

