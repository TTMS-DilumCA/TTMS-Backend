package com.TTMSGislavedGummiLanka.TTMS_Backend.repo;

import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.Role;
import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepo extends MongoRepository<User, String>, CustomUserRepo {

    Optional<User> findByEmail(String email);
    User findByRole(Role role);
    boolean existsByEmail(String email);
}