package com.divyanshu.FinanceLedger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.divyanshu.FinanceLedger.model.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface userrepo extends JpaRepository<users,Long> {
    Optional<users> findByEmail(String email);
    Optional<users> findByName(String name);
}
