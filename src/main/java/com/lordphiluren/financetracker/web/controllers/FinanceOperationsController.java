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
@RequestMapping("/api")
public class FinanceOperationsController {
    private final FinanceOperationsService financeOperationsService;
    private final ModelsMapper modelsMapper;
    @Autowired
    public FinanceOperationsController(FinanceOperationsService financeOperationsService,
                                       ModelsMapper modelsMapper) {
        this.financeOperationsService = financeOperationsService;
        this.modelsMapper = modelsMapper;
    }
    @GetMapping("/{type}")
    public ResponseEntity<?> getUserOperations(@PathVariable String type,
                                               @AuthenticationPrincipal UserDetailsImpl userPrincipal) {
        if(type.equals("expenses")) {
            List<FinanceOperationDTO> financeOperationDTOS = financeOperationsService
                    .getUserExpenses(userPrincipal.getUser())
                    .stream()
                    .map(modelsMapper::makeFinanceOperationDTO)
                    .toList();
            return new ResponseEntity<>(financeOperationDTOS, HttpStatus.OK);
        }
        if(type.equals("incomes")) {
            List<FinanceOperationDTO> financeOperationDTOS = financeOperationsService
                    .getUserIncomes(userPrincipal.getUser())
                    .stream()
                    .map(modelsMapper::makeFinanceOperationDTO)
                    .toList();
            return new ResponseEntity<>(financeOperationDTOS, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Invalid value of type parameter", HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/{type}")
    public ResponseEntity<?> addFinanceOperation(@RequestBody NewFinanceOperationDTO operationDTO,
                                                 @PathVariable String type) {
        if(type.equals("expenses")) {
            financeOperationsService.addExpense(modelsMapper.makeFinanceOperation(operationDTO));
            return ResponseEntity.ok("Expense added successfully");
        }
        else if(type.equals("incomes")) {
            financeOperationsService.addIncome(modelsMapper.makeFinanceOperation(operationDTO));
            return ResponseEntity.ok("Income added successfully");
        }
        else {
            return new ResponseEntity<>("Invalid value of type parameter", HttpStatus.BAD_REQUEST);
        }
    }
    @ExceptionHandler
    private ResponseEntity<ControllerErrorResponse> handleException(RuntimeException e) {
        ControllerErrorResponse errorResponse = new ControllerErrorResponse(
                e.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
