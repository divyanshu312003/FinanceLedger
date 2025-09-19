package com.divyanshu.FinanceLedger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.divyanshu.FinanceLedger.repository.*;
import com.divyanshu.FinanceLedger.model.*;
import java.util.*;

import java.time.LocalDateTime;

@Service
public class accountservice {
    @Autowired
    private accountrepo accountrepo;
    @Autowired
    private transactionrepo transactionrepo;
    @Autowired
    private userrepo userrepo;
    public void deposit(long account, double amount) {
        accounts account1 = accountrepo.findByAccountNumber(account)
                .orElseThrow(() -> new RuntimeException("Account not found: " + account));
        account1.setBalance(account1.getBalance()+amount);
        accountrepo.save(account1);

        transactions txn = new transactions();
        txn.setAmount(amount);
        txn.setType("Deposit");
        txn.setTimestamp(LocalDateTime.now());
        txn.setFromAccount(0L);
        txn.setLocationChange(false);
        txn.setToAccount(account);
        transactionrepo.save(txn);
    }

    public void transfer(long from, long to, double amount, boolean locationChange) {
        accounts fromdetail=accountrepo.findByAccountNumber(from).orElseThrow();
        accounts todetail=accountrepo.findByAccountNumber(to).orElseThrow();
        fromdetail.setBalance(fromdetail.getBalance()-amount);
        todetail.setBalance(todetail.getBalance()+amount);
        accountrepo.saveAll(List.of(fromdetail,todetail));
        transactionrepo.save(new transactions(null,amount,"transfer",LocalDateTime.now(),locationChange,from,to));
    }
    public Optional<accounts> getUserByEmail(String email) {
        return accountrepo.findByUser_email(email);
    }
}
