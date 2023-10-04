package com.lordphiluren.financetracker.rest;

import com.lordphiluren.financetracker.factory.ModelsDTOFactory;
import com.lordphiluren.financetracker.models.User;
import com.lordphiluren.financetracker.rest.dto.AccountDTO;
import com.lordphiluren.financetracker.security.UserDetailsImpl;
import com.lordphiluren.financetracker.services.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accounts")
public class AccountsController {
    private final ModelsDTOFactory modelsDTOFactory;
    private final AccountsService accountsService;
    @Autowired
    public AccountsController(ModelsDTOFactory modelsDTOFactory, AccountsService accountsService) {
        this.modelsDTOFactory = modelsDTOFactory;
        this.accountsService = accountsService;
    }

    @GetMapping()
    public List<AccountDTO> getUserAccounts(@AuthenticationPrincipal UserDetailsImpl userPrincipal) {
        User user = userPrincipal.getUser();
        return accountsService.getAccountsByUserId(user.getId())
                .stream()
                .map(modelsDTOFactory::makeAccountDTO)
                .collect(Collectors.toList());
    }

    @PostMapping()
    public ResponseEntity<?> addUserAccount(@RequestBody AccountDTO accountDTO,
                                            @AuthenticationPrincipal UserDetailsImpl userPrincipal) {
        accountsService.addUserAccount(modelsDTOFactory.makeAccount(accountDTO),
                userPrincipal.getUser());
        return ResponseEntity.ok("Account added successfully");
    }
}
