package com.divyanshu.FinanceLedger.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.divyanshu.FinanceLedger.model.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OTPrepo extends JpaRepository<OTPtable, Long> {
    Optional<OTPtable> findTopByEmailOrderByGeneratedAtDesc(String email);
}
