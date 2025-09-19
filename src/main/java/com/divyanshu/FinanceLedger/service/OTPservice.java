package com.divyanshu.FinanceLedger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.divyanshu.FinanceLedger.repository.OTPrepo;
import com.divyanshu.FinanceLedger.model.OTPtable;
import java.util.Optional;
import java.util.Random;
import java.time.LocalDateTime;

@Service
public class OTPservice {

    @Autowired
    private  OTPrepo otprepo;

    @Autowired
    private  SmsService smsService;

    public  void generateOtp(String email, String phoneNumber) {
        String otp = String.valueOf(100000 + new Random().nextInt(900000));

        OTPtable otpEntity = new OTPtable();
        otpEntity.setEmail(email);
        otpEntity.setOtp(otp);
        otpEntity.setGeneratedAt(LocalDateTime.now());
        otpEntity.setVerified(false);

        otprepo.save(otpEntity);

        smsService.sendSms(phoneNumber, "Your OTP for money transfer is: " + otp);
    }

    public  boolean verifyOtp(String email, String otp) {
        return otprepo.findTopByEmailOrderByGeneratedAtDesc(email)
                .filter(o -> !o.isVerified() && o.getOtp().equals(otp) &&
                        o.getGeneratedAt().isAfter(LocalDateTime.now().minusMinutes(5)))
                .map(entity -> {
                    entity.setVerified(true);
                    otprepo.save(entity);
                    return true;
                }).orElse(false);
    }
}

