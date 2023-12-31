package com.lordphiluren.financetracker.repositories;

import com.lordphiluren.financetracker.models.Account;
import com.lordphiluren.financetracker.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountsRepository extends JpaRepository<Account, Long> {
    List<Account> findByUser(User user);
}
