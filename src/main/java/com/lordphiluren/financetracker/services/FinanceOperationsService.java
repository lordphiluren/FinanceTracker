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

    public List<FinanceOperation> getAllExpensesByUserId(int user_id) {
        List<Account> userAccounts = accountsService.getAccountsByUserId(user_id);
        List<FinanceOperation> expenses = new ArrayList<>();
        for (Account userAccount : userAccounts) {
            expenses.addAll(userAccount.getFinanceOperations()
                    .stream()
                    .filter(x -> !x.isIncomeOperation())
                    .toList());
        }
        return expenses;
    }
}
