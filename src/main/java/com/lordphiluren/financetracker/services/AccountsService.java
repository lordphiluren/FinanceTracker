package com.lordphiluren.financetracker.services;

import com.lordphiluren.financetracker.models.Account;
import com.lordphiluren.financetracker.models.User;
import com.lordphiluren.financetracker.repositories.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountsService {
    private final AccountsRepository accountsRepository;
    private final UsersService usersService;

    @Autowired
    public AccountsService(AccountsRepository accountsRepository, UsersService usersService) {
        this.accountsRepository = accountsRepository;
        this.usersService = usersService;
    }

    public List<Account> getAccountsByUserId(int user_id) {
        User user = usersService.getUserById(user_id);
        return accountsRepository.findByUser(user);
    }
}
