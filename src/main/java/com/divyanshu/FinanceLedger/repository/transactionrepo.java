package com.divyanshu.FinanceLedger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.divyanshu.FinanceLedger.model.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface transactionrepo extends JpaRepository<transactions,Long> {
    List<transactions> findByFromAccount(long accountNumber);
}
