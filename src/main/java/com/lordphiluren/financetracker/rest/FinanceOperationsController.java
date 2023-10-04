package com.lordphiluren.financetracker.rest;

import com.lordphiluren.financetracker.models.FinanceOperation;
import com.lordphiluren.financetracker.rest.dto.FinanceOperationDTO;
import com.lordphiluren.financetracker.factory.ModelsDTOFactory;
import com.lordphiluren.financetracker.models.User;
import com.lordphiluren.financetracker.security.UserDetailsImpl;
import com.lordphiluren.financetracker.services.FinanceOperationsService;
import com.lordphiluren.financetracker.utils.exceptions.AccountNotFoundException;
import com.lordphiluren.financetracker.utils.exceptions.ControllerErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class FinanceOperationsController {
    private final FinanceOperationsService financeOperationsService;
    private final ModelsDTOFactory modelsDTOFactory;
    @Autowired
    public FinanceOperationsController(FinanceOperationsService financeOperationsService,
                                       ModelsDTOFactory modelsDTOFactory) {
        this.financeOperationsService = financeOperationsService;
        this.modelsDTOFactory = modelsDTOFactory;
    }

    @GetMapping("/expenses")
    public List<FinanceOperationDTO> getUserExpenses(@AuthenticationPrincipal UserDetailsImpl userPrincipal) {
        User user = userPrincipal.getUser();
        return financeOperationsService.getUserExpenses(user)
                .stream()
                .map(modelsDTOFactory::makeFinanceOperationDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/expenses")
    public ResponseEntity<?> addExpense(@RequestBody FinanceOperationDTO expense,
                                        @AuthenticationPrincipal UserDetailsImpl userPrincipal) {
        financeOperationsService.addExpense(modelsDTOFactory.makeFinanceOperation(expense),
                userPrincipal.getUser());
        return ResponseEntity.ok("Expense added successfully");
    }


    @GetMapping("/incomes")
    public List<FinanceOperationDTO> getUserIncomes(@AuthenticationPrincipal UserDetailsImpl userPrincipal) {
        User user = userPrincipal.getUser();
        return financeOperationsService.getUserIncomes(user)
                .stream()
                .map(modelsDTOFactory::makeFinanceOperationDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/incomes")
    public ResponseEntity<?> addIncome(@RequestBody FinanceOperationDTO income,
                                        @AuthenticationPrincipal UserDetailsImpl userPrincipal) {
        financeOperationsService.addIncome(modelsDTOFactory.makeFinanceOperation(income),
                userPrincipal.getUser());
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
