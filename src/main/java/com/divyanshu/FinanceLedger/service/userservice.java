package com.divyanshu.FinanceLedger.service;

import com.divyanshu.FinanceLedger.model.accounts;
import com.divyanshu.FinanceLedger.model.users;
import com.divyanshu.FinanceLedger.model.Role;
import com.divyanshu.FinanceLedger.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class userservice implements UserDetailsService {

    @Autowired
    private userrepo userRepository;
    @Autowired
    private accountrepo accountrepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRole().name())
                .build();

    }

    public users register(users user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists with email: " + user.getEmail());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRole() == null) {
            user.setRole(Role.CUSTOMER);
        }

        users savedUser = userRepository.save(user);


        accounts acc = new accounts(0.0, savedUser.getName(), savedUser);
        accountrepo.save(acc);

        return savedUser;
    }

    public Optional<users> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}