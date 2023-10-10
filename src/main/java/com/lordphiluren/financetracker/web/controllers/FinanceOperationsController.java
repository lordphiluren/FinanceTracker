package com.lordphiluren.financetracker.web.controllers;

import com.lordphiluren.financetracker.web.dto.FinanceOperationDTO;
import com.lordphiluren.financetracker.web.dto.NewFinanceOperationDTO;
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
@RequestMapping("/api/operations")
public class FinanceOperationsController {
    private final FinanceOperationsService financeOperationsService;
    private final ModelsMapper modelsMapper;
    @Autowired
    public FinanceOperationsController(FinanceOperationsService financeOperationsService,
                                       ModelsMapper modelsMapper) {
        this.financeOperationsService = financeOperationsService;
        this.modelsMapper = modelsMapper;
    }
    @GetMapping("/expenses")
    public List<FinanceOperationDTO> getExpenses(@AuthenticationPrincipal UserDetailsImpl userPrincipal) {
        return financeOperationsService
                .getUserExpenses(userPrincipal.getUser())
                .stream()
                .map(modelsMapper::makeFinanceOperationDTO)
                .toList();
    }
    @GetMapping("/incomes")
    public List<FinanceOperationDTO> getIncomes(@AuthenticationPrincipal UserDetailsImpl userPrincipal) {
        return financeOperationsService
                .getUserIncomes(userPrincipal.getUser())
                .stream()
                .map(modelsMapper::makeFinanceOperationDTO)
                .toList();
    }
    @PostMapping("/expenses")
    public ResponseEntity<?> addExpense(@RequestBody NewFinanceOperationDTO operationDTO) {
        financeOperationsService.addExpense(modelsMapper.makeFinanceOperation(operationDTO));
        return ResponseEntity.ok("Expense added successfully");
    }
    @PostMapping("/incomes")
    public ResponseEntity<?> addIncome(@RequestBody NewFinanceOperationDTO operationDTO) {
        financeOperationsService.addIncome(modelsMapper.makeFinanceOperation(operationDTO));
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
