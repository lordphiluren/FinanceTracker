package com.lordphiluren.financetracker.services;

import com.lordphiluren.financetracker.models.Account;
import com.lordphiluren.financetracker.models.Category;
import com.lordphiluren.financetracker.models.FinanceOperation;
import com.lordphiluren.financetracker.models.User;
import com.lordphiluren.financetracker.repositories.FinanceOperationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinanceOperationsService {
    private final AccountsService accountsService;
    private final CategoriesService categoriesService;
    private final FinanceOperationsRepository financeOperationsRepository;

    @Autowired
    public FinanceOperationsService(AccountsService accountsService, CategoriesService categoriesService, FinanceOperationsRepository financeOperationsRepository) {
        this.accountsService = accountsService;
        this.categoriesService = categoriesService;
        this.financeOperationsRepository = financeOperationsRepository;
    }
    @Transactional
    public List<FinanceOperation> getUserIncomes(User user) {
        List<Account> userAccounts = accountsService.getAccountsByUserId(user.getId());
        return userAccounts.stream()
                .flatMap(userAccount -> userAccount.getFinanceOperations()
                        .stream()
                        .filter(FinanceOperation::isIncomeOperation))
                .collect(Collectors.toList());
    }
    @Transactional
    public List<FinanceOperation> getUserExpenses(User user) {
        List<Account> userAccounts = accountsService.getAccountsByUserId(user.getId());
        return userAccounts.stream()
                .flatMap(userAccount -> userAccount.getFinanceOperations()
                        .stream()
                        .filter(x -> !x.isIncomeOperation()))
                .collect(Collectors.toList());
    }
    @Transactional
    public void addExpense(FinanceOperation expense, User user){
        expense.setIncomeOperation(false);
        Account account = accountsService.getAccountByUserAndName(user, expense.getAccount().getName());
        Category category = categoriesService.getCategoryByUserAndName(user, expense.getCategory().getName());
        BigDecimal newBalance = account.getBalance().subtract(expense.getAmount());
        account.setBalance(newBalance);
        expense.setAccount(account);
        expense.setCategory(category);
        financeOperationsRepository.save(expense);
    }
    @Transactional
    public void addIncome(FinanceOperation income, User user){
        income.setIncomeOperation(true);
        financeOperationsRepository.save(income);
    }

}
