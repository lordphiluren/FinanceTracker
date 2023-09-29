package com.lordphiluren.financetracker.repositories;

import com.lordphiluren.financetracker.models.FinanceOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinanceOperationsRepository extends JpaRepository<FinanceOperation, Long> {
}
