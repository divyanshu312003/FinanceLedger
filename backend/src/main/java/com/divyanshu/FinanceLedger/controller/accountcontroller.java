package com.divyanshu.FinanceLedger.controller;
import com.divyanshu.FinanceLedger.model.accounts;
import com.divyanshu.FinanceLedger.model.transactions;
import com.divyanshu.FinanceLedger.model.users;
import com.divyanshu.FinanceLedger.service.*;
import com.divyanshu.FinanceLedger.config.*;
import com.divyanshu.FinanceLedger.repository.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@RestController
@RequestMapping("/accounts")
public class accountcontroller {

    @Autowired
    private userservice userservice;
    @Autowired
    private SmsService smsService;
    @Autowired
    private transactionservice transactionservice;

    @Autowired
    private accountservice accountservice;

    @Autowired
    private jwtutil jwtutil;

    @Autowired
    private accountrepo accountrepo;
    @Autowired
    private OTPservice otpService;


    @GetMapping("/me")
    public ResponseEntity<accounts> getLoggedInUser(Authentication authentication) {
        String email = authentication.getName();
        accounts account = accountrepo.findByUser_email(email)
                .orElseThrow(() -> new RuntimeException(email));
        return ResponseEntity.ok(account);
    }
    @GetMapping("/me/transactions")
    public ResponseEntity<List<transactions>> getbankstatements(Authentication authentication) {
        String email = authentication.getName();
        accounts account = accountrepo.findByUser_email(email)
                .orElseThrow(() -> new RuntimeException(email));

        return ResponseEntity.ok(transactionservice.getTransactionsByAccount(account.getAccountNumber()));
    }
    @GetMapping("/me/transactions/download")
    public void downloadTransactionsCsv(Authentication authentication, HttpServletResponse response) throws IOException {
        String email = authentication.getName();
        accounts account = accountrepo.findByUser_email(email)
                .orElseThrow(() -> new RuntimeException(email));
        long accountNumber=account.getAccountNumber();
        List<transactions> txns = transactionservice.getTransactionsByAccount(accountNumber);

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"transactions.csv\"");

        PrintWriter writer = response.getWriter();
        writer.println("ID,From Account,To Account,Amount,Date,Location Change,Type");

        for (transactions txn : txns) {
            writer.println(txn.getId() + "," +
                    txn.getFromAccount() + "," +
                    txn.getToAccount() + "," +
                    txn.getAmount() + "," +
                    txn.getTimestamp() + "," +
                    txn.isLocationChange() + "," +
                    txn.getType());

        }

        writer.flush();
        writer.close();
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestHeader("Authorization") String authentication,
                                     @RequestParam double amount) {
        String token = authentication.replace("Bearer ", "");
        String email = jwtutil.extractUsername(token);

        accounts account = accountrepo.findByUser_email(email)
                .orElseThrow(() -> new RuntimeException("Account not found for user"));

        accountservice.deposit(account.getAccountNumber(), amount);
        smsService.sendSms(account.getUser().getPhoneNumber(), "₹" + amount + " has been credited to your account.");

        return ResponseEntity.ok("Amount Deposited Successfully to account: " + account.getAccountNumber());

    }

    @PostMapping("/transfer/confirm")
    public ResponseEntity<?> confirmTransfer(
            @RequestHeader("Authorization") String token,
            @RequestParam long to,
            @RequestParam double amount,
            @RequestParam boolean locationChange,
            @RequestParam String otp) {

        String email = jwtutil.extractUsername(token.replace("Bearer ", ""));
        accounts fromAccount = accountrepo.findByUser_email(email)
                .orElseThrow(() -> new RuntimeException("Account not found for user"));

        boolean isValid = otpService.verifyOtp(email, otp);

        if (!isValid) {
            return ResponseEntity.badRequest().body("Invalid or expired OTP.");
        }

        accountservice.transfer(fromAccount.getAccountNumber(), to, amount, locationChange);

        return ResponseEntity.ok("Transfer confirmed and completed.");
    }

    @PostMapping("/transfer/request-otp")
    public ResponseEntity<?> requestOtp(@RequestHeader("Authorization") String token) {
        String email = jwtutil.extractUsername(token.replace("Bearer ", ""));
        accounts account = accountrepo.findByUser_email(email)
                .orElseThrow(() -> new RuntimeException("Account not found for user"));
        users user = account.getUser();
        otpService.generateOtp(email, user.getPhoneNumber());
        return ResponseEntity.ok("OTP sent to your registered number.");
    }

}

