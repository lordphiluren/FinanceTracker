package com.lordphiluren.financetracker.services;

import com.lordphiluren.financetracker.models.Account;
import com.lordphiluren.financetracker.models.User;
import com.lordphiluren.financetracker.repositories.AccountsRepository;
import com.lordphiluren.financetracker.utils.exceptions.AccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Account> getAccountsByUserId(long user_id) {
        User user = usersService.getUserById(user_id);
        return accountsRepository.findByUser(user);
    }
    @Transactional
    public Account getAccountByUserAndName(User user, String name) {
        Optional<Account> account = accountsRepository.findByUserAndName(user, name);
        if(account.isEmpty()) {
            throw new AccountNotFoundException("Account not found");
        }
        return account.get();
    }
    @Transactional
    public void addUserAccount(Account account, User user) {
        account.setUser(user);
        account.setFinanceOperations(Collections.emptyList());
        accountsRepository.save(account);
    }
}
