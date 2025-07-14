package com.divyanshu.FinanceLedger.controller;
import org.springframework.security.core.userdetails.UserDetails;
import com.divyanshu.FinanceLedger.config.jwtutil;
import com.divyanshu.FinanceLedger.dto.*;
import com.divyanshu.FinanceLedger.model.*;
import com.divyanshu.FinanceLedger.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class authcontroller {

    @Autowired
    private userservice userService;
    @Autowired
    private AuthenticationManager authManager;

    @Autowired private jwtutil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody users user) {
        return ResponseEntity.ok(userService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody authrequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetails user = userService.loadUserByUsername(request.getEmail());
        String token = jwtUtil.generateToken(user);
        return ResponseEntity.ok(new authresponse(token));
    }
}
