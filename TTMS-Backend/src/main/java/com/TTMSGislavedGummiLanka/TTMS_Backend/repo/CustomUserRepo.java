package com.TTMSGislavedGummiLanka.TTMS_Backend.repo;

public interface CustomUserRepo {
    void updatePasswordByEmail(String email, String password);
}