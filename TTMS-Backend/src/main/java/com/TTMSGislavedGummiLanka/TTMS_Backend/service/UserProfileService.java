package com.TTMSGislavedGummiLanka.TTMS_Backend.service;

import com.TTMSGislavedGummiLanka.TTMS_Backend.dto.UserProfileUpdateRequest;
import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.User;

public interface UserProfileService {
    User getCurrentUserProfile(String email);
    User updateUserProfile(String email, UserProfileUpdateRequest request);
}