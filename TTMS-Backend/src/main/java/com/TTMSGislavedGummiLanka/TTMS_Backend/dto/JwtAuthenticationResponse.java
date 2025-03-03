package com.TTMSGislavedGummiLanka.TTMS_Backend.dto;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String token;
    private String refreshToken;
}
