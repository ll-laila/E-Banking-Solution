package com.example.user.users.repository;

import com.example.user.users.entity.Client;
import com.example.user.users.entity.Role;
import com.example.user.users.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findByEmail(String email);

    Optional<User> findById(String id);

    List<User> findByRole(Role role);

    Optional<User> findUserByPhoneNumber(String phone);


/*//-------------------------chaima------------------------------//
@Query(value = "{ 'phoneNumber': ?0 }", fields = "{ '_id': 1 }")
User findIdByPhoneNumber(String phoneNumber);*/
List<User> findAllByAgentId(String agentId);
}
