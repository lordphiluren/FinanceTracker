package com.lordphiluren.financetracker.rest;

import com.lordphiluren.financetracker.rest.dto.FinanceOperationDTO;
import com.lordphiluren.financetracker.factory.ModelsDTOFactory;
import com.lordphiluren.financetracker.models.User;
import com.lordphiluren.financetracker.security.UserDetailsImpl;
import com.lordphiluren.financetracker.services.FinanceOperationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public List<FinanceOperationDTO> getUserExpenses(@AuthenticationPrincipal UserDetailsImpl userPrincipal) {
        User user = userPrincipal.getUser();
        return financeOperationsService.getUserExpenses(user)
                .stream()
                .map(modelsDTOFactory::makeFinanceOperationDTO)
                .collect(Collectors.toList());
    }
    @GetMapping("/incomes")
    public List<FinanceOperationDTO> getUserIncomes(@AuthenticationPrincipal UserDetailsImpl userPrincipal) {
        User user = userPrincipal.getUser();
        return financeOperationsService.getUserIncomes(user)
                .stream()
                .map(modelsDTOFactory::makeFinanceOperationDTO)
                .collect(Collectors.toList());
    }
}
