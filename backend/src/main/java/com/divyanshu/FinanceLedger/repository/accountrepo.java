package com.divyanshu.FinanceLedger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.divyanshu.FinanceLedger.model.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface accountrepo extends JpaRepository<accounts,Long> {
    Optional<accounts> findByAccountNumber(long accountNumber);

    Optional<accounts> findByUser_email(String useremail);
}
