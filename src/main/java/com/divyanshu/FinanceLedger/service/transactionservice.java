package com.divyanshu.FinanceLedger.service;

import com.divyanshu.FinanceLedger.model.transactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.divyanshu.FinanceLedger.repository.transactionrepo;
import java.util.*;
@Service
public class transactionservice {
    @Autowired
    private  transactionrepo transactionsRepo;

    public  List<transactions> getTransactionsByAccount(long accountNumber) {
        return transactionsRepo.findByFromAccount(accountNumber);
    }
}
