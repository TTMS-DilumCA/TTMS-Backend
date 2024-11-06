package com.TTMSGislavedGummiLanka.TTMS_Backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor

public class UserController {

    public ResponseEntity<String> sayHello(){
        return ResponseEntity.ok("Hello USER");
    }
}
