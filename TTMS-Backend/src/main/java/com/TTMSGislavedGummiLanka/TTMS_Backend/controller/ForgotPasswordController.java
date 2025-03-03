package com.TTMSGislavedGummiLanka.TTMS_Backend.controller;

import com.TTMSGislavedGummiLanka.TTMS_Backend.dto.ChangePassword;
import com.TTMSGislavedGummiLanka.TTMS_Backend.dto.MailBody;
import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.Otp;
import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.User;
import com.TTMSGislavedGummiLanka.TTMS_Backend.repo.OtpRepo;
import com.TTMSGislavedGummiLanka.TTMS_Backend.repo.UserRepo;
import com.TTMSGislavedGummiLanka.TTMS_Backend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

@RestController
@RequestMapping("/forgotPassword")
public class ForgotPasswordController {

    private final UserRepo userRepo;
    private final OtpRepo otpRepo;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ForgotPasswordController(UserRepo userRepo, OtpRepo otpRepo, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.otpRepo = otpRepo;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/verifyEmail/{email}")
    public ResponseEntity<String> verifyEmail(@PathVariable String email) {
        // Check if user exists
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Please provide a valid email"));

        // Generate OTP (as an int)
        int otp = otpGenerator();

        // Check if OTP record already exists for the email
        Otp otpEntity = otpRepo.findByEmail(email).orElse(new Otp());
        otpEntity.setEmail(email);
        otpEntity.setOtp(otp);
        otpEntity.setExpiryTime(LocalDateTime.now().plusSeconds(30)); // Set expiry to 30 seconds

        // Save or update OTP in the database
        otpRepo.save(otpEntity);

        // Send OTP via email
        MailBody mailBody = MailBody.builder()
                .to(email)
                .text("This is the OTP for your forgot password request: " + otp) // Explicit conversion
                .subject("OTP for Forgot Password Request")
                .build();

        emailService.sendSimpleMessage(mailBody);

        return ResponseEntity.ok("OTP sent successfully to " + email);
    }

    // to verify the otp
    @PostMapping("/verifyOtp/{otp}/{email}")
    public ResponseEntity<String> verifyOtp(@PathVariable Integer otp, @PathVariable String email) {
        // Check if user exists
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Please provide a valid email"));

        // Find OTP by email and otp
        Otp otpEntity = otpRepo.findByEmailAndOtp(email, otp)
                .orElseThrow(() -> new IllegalArgumentException("Invalid OTP or user"));

        // Check if OTP is expired
        if (otpEntity.getExpiryTime().isBefore(LocalDateTime.now())) {
            // Delete the OTP from the database
            otpRepo.delete(otpEntity);
            return ResponseEntity.status(400).body("OTP has expired");
        }

        return ResponseEntity.ok("OTP verified successfully");
    }

    // to change password
  @PostMapping("/changePassword/{email}")
public ResponseEntity<String> changePasswordHandler(@RequestBody ChangePassword changePassword, @PathVariable String email) {

    if (!Objects.equals(changePassword.password(), changePassword.repeatPassword())) {
        return new ResponseEntity<>("Passwords do not match", HttpStatus.BAD_REQUEST);
    }

    String encodedPassword = passwordEncoder.encode(changePassword.password());
    userRepo.updatePasswordByEmail(email, encodedPassword);
    return ResponseEntity.ok("Password changed successfully");
}
    private int otpGenerator() {
        Random random = new Random();
        return random.nextInt(900_000) + 100_000; // Generates a 6-digit OTP as an int
    }
}