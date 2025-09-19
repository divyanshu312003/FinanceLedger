package com.divyanshu.FinanceLedger.controller;
import com.divyanshu.FinanceLedger.model.*;
import com.divyanshu.FinanceLedger.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class usercontroller {
    @Autowired
    private userservice userservice;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody users user){
        return ResponseEntity.ok(userservice.register(user));
    }
}
