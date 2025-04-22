package com.TTMSGislavedGummiLanka.TTMS_Backend.service;

import com.TTMSGislavedGummiLanka.TTMS_Backend.dto.JwtAuthenticationResponse;
import com.TTMSGislavedGummiLanka.TTMS_Backend.dto.RefreshTokenRequest;
import com.TTMSGislavedGummiLanka.TTMS_Backend.dto.SignUpRequest;
import com.TTMSGislavedGummiLanka.TTMS_Backend.dto.SigninRequest;
import com.TTMSGislavedGummiLanka.TTMS_Backend.entity.User;

public interface AuthenticationService {
    User signup(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signin(SigninRequest signinRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
