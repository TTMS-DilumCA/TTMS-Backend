package com.TTMSGislavedGummiLanka.TTMS_Backend.repo;

import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CustomerRepo extends MongoRepository<Customer, String> {
    Optional<Customer> findByEmail(String email);
}