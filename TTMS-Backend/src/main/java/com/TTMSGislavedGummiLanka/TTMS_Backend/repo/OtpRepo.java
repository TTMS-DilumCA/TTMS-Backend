package com.TTMSGislavedGummiLanka.TTMS_Backend.repo;
import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.Otp;
import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;


public interface OtpRepo extends  MongoRepository<Otp, String> {
    Optional<Otp> findByEmailAndOtp(String email, String otp);
    Optional<Otp> findByEmail(String email);
    Optional<Otp> findByEmailAndOtp(String email, Integer otp);
}