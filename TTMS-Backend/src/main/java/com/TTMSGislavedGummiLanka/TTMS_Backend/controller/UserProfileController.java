package com.TTMSGislavedGummiLanka.TTMS_Backend.controller;

import com.TTMSGislavedGummiLanka.TTMS_Backend.dto.UserProfileUpdateRequest;
import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.User;
import com.TTMSGislavedGummiLanka.TTMS_Backend.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping
    public ResponseEntity<User> getUserProfile(Authentication authentication) {
        User user = userProfileService.getCurrentUserProfile(authentication.getName());
        return ResponseEntity.ok(user);
    }

    @PutMapping
    public ResponseEntity<User> updateUserProfile(
            @RequestBody UserProfileUpdateRequest request,
            Authentication authentication) {
        User updatedUser = userProfileService.updateUserProfile(authentication.getName(), request);
        return ResponseEntity.ok(updatedUser);
    }
}