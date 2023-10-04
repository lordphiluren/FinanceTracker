package com.lordphiluren.financetracker.repositories;

import com.lordphiluren.financetracker.models.FinanceOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FinanceOperationsRepository extends JpaRepository<FinanceOperation, Long> {
    Optional<FinanceOperation> findByIdAndIncomeOperationTrue(long id);
    Optional<FinanceOperation> findByIdAndIncomeOperationFalse(long id);
}
