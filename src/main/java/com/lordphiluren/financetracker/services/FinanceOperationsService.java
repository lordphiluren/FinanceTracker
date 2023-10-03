package com.lordphiluren.financetracker.services;

import com.lordphiluren.financetracker.models.Account;
import com.lordphiluren.financetracker.models.FinanceOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinanceOperationsService {
    private final AccountsService accountsService;
    @Autowired
    public FinanceOperationsService(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    public List<FinanceOperation> getIncomesByUserId(long user_id) {
        List<Account> userAccounts = accountsService.getAccountsByUserId(user_id);
        return userAccounts.stream()
                .flatMap(userAccount -> userAccount.getFinanceOperations()
                        .stream()
                        .filter(FinanceOperation::isIncomeOperation))
                .collect(Collectors.toList());
    }
    public List<FinanceOperation> getExpensesByUserId(long user_id) {
        List<Account> userAccounts = accountsService.getAccountsByUserId(user_id);
        return userAccounts.stream()
                .flatMap(userAccount -> userAccount.getFinanceOperations()
                        .stream()
                        .filter(x -> !x.isIncomeOperation()))
                .collect(Collectors.toList());
    }
}
