package com.lordphiluren.financetracker.services;

import com.lordphiluren.financetracker.models.Account;
import com.lordphiluren.financetracker.models.User;
import com.lordphiluren.financetracker.repositories.AccountsRepository;
import com.lordphiluren.financetracker.utils.exceptions.AccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AccountsService {
    private final AccountsRepository accountsRepository;
    private final UsersService usersService;

    @Autowired
    public AccountsService(AccountsRepository accountsRepository, UsersService usersService) {
        this.accountsRepository = accountsRepository;
        this.usersService = usersService;
    }
    @Transactional
    public List<Account> getAccountsByUser(User user) {
        return accountsRepository.findByUser(user);
    }
    @Transactional
    public void addAccount(Account account) {
        account.setFinanceOperations(Collections.emptyList());
        accountsRepository.save(account);
    }
    @Transactional
    public Account getAccountById(long id) {
        return accountsRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("Account not found"));
    }

    @Transactional
    public void updateAccount(Account account) {
        accountsRepository.save(account);
    }
    @Transactional
    public void deleteAccount(long id) {
        accountsRepository.delete(getAccountById(id));
    }
}
