package com.TTMSGislavedGummiLanka.TTMS_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileUpdateRequest {
    private String firstname;
    private String lastname;
    private String fullname;
    private String profileImageUrl;
}