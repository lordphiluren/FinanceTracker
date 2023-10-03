package com.lordphiluren.financetracker.controllers;

import com.lordphiluren.financetracker.dto.FinanceOperationDTO;
import com.lordphiluren.financetracker.factory.ModelsDTOFactory;
import com.lordphiluren.financetracker.models.FinanceOperation;
import com.lordphiluren.financetracker.services.FinanceOperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public List<FinanceOperationDTO> getUserExpenses(@RequestParam(name = "id") long userId) {
        return financeOperationsService.getExpensesByUserId(userId)
                .stream()
                .map(modelsDTOFactory::makeFinanceOperationDTO)
                .collect(Collectors.toList());
    }
    @GetMapping("/incomes")
    public List<FinanceOperationDTO> getUserIncomes(@RequestParam(name = "id") long userId) {
        return financeOperationsService.getIncomesByUserId(userId)
                .stream()
                .map(modelsDTOFactory::makeFinanceOperationDTO)
                .collect(Collectors.toList());
    }
}
