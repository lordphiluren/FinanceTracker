package com.lordphiluren.financetracker.web.controllers;

import com.lordphiluren.financetracker.web.dto.FinanceOperationDTO;
import com.lordphiluren.financetracker.web.mappers.ModelsMapper;
import com.lordphiluren.financetracker.models.User;
import com.lordphiluren.financetracker.security.UserDetailsImpl;
import com.lordphiluren.financetracker.services.FinanceOperationsService;
import com.lordphiluren.financetracker.utils.exceptions.ControllerErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class FinanceOperationsController {
    //TODO
    // - validation
    // - getting specific operation details
    // - updating operation
    // - deleting operation
    private final FinanceOperationsService financeOperationsService;
    private final ModelsMapper modelsMapper;
    @Autowired
    public FinanceOperationsController(FinanceOperationsService financeOperationsService,
                                       ModelsMapper modelsMapper) {
        this.financeOperationsService = financeOperationsService;
        this.modelsMapper = modelsMapper;
    }
    @GetMapping("/expenses")
    public List<FinanceOperationDTO> getUserExpenses(@AuthenticationPrincipal UserDetailsImpl userPrincipal) {
        User user = userPrincipal.getUser();
        return financeOperationsService.getUserExpenses(user)
                .stream()
                .map(modelsMapper::makeFinanceOperationDTO)
                .collect(Collectors.toList());
    }
    @PostMapping("/expenses")
    public ResponseEntity<?> addExpense(@RequestBody FinanceOperationDTO expense) {
        financeOperationsService.addExpense(modelsMapper.makeFinanceOperation(expense));
        return ResponseEntity.ok("Expense added successfully");
    }
    @GetMapping("/incomes")
    public List<FinanceOperationDTO> getUserIncomes(@AuthenticationPrincipal UserDetailsImpl userPrincipal) {
        User user = userPrincipal.getUser();
        return financeOperationsService.getUserIncomes(user)
                .stream()
                .map(modelsMapper::makeFinanceOperationDTO)
                .collect(Collectors.toList());
    }
    @PostMapping("/incomes")
    public ResponseEntity<?> addIncome(@RequestBody FinanceOperationDTO income) {
        financeOperationsService.addIncome(modelsMapper.makeFinanceOperation(income));
        return ResponseEntity.ok("Income added successfully");
    }
    @ExceptionHandler
    private ResponseEntity<ControllerErrorResponse> handleException(RuntimeException e) {
        ControllerErrorResponse errorResponse = new ControllerErrorResponse(
                e.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
