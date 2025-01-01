package com.example.user.users.repository;

import com.example.user.users.entity.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AdminRepository extends MongoRepository<Admin, String> {

    boolean existsByPhoneNumber(String phoneNumber);
    Optional<Admin> findByPhoneNumber(String phoneNumber);
}
