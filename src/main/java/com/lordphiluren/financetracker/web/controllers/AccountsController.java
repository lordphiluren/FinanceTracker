package com.lordphiluren.financetracker.web.controllers;

import com.lordphiluren.financetracker.models.Account;
import com.lordphiluren.financetracker.utils.exceptions.ControllerErrorResponse;
import com.lordphiluren.financetracker.web.mappers.ModelsMapper;
import com.lordphiluren.financetracker.models.User;
import com.lordphiluren.financetracker.web.dto.AccountDTO;
import com.lordphiluren.financetracker.security.UserDetailsImpl;
import com.lordphiluren.financetracker.services.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/accounts")
public class AccountsController {
    private final ModelsMapper modelsMapper;
    private final AccountsService accountsService;
    @Autowired
    public AccountsController(ModelsMapper modelsMapper, AccountsService accountsService) {
        this.modelsMapper = modelsMapper;
        this.accountsService = accountsService;
    }
    @GetMapping()
    public List<AccountDTO> getAccounts(@AuthenticationPrincipal UserDetailsImpl userPrincipal) {
        return accountsService.getAccountsByUser(userPrincipal.getUser())
                .stream()
                .map(modelsMapper::makeAccountDTO)
                .collect(Collectors.toList());
    }
    @PostMapping()
    public ResponseEntity<?> addAccount(@RequestBody AccountDTO accountDTO,
                                        @AuthenticationPrincipal UserDetailsImpl userPrincipal) {
        Account account = modelsMapper.makeAccount(accountDTO);
        account.setUser(userPrincipal.getUser());
        accountsService.addAccount(account);
        return ResponseEntity.ok("Account added successfully");
    }
    @GetMapping("/{id}")
    public AccountDTO getAccountById(@PathVariable long id) {
        return modelsMapper.makeAccountDTO(accountsService.getAccountById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable long id, @RequestBody AccountDTO accountDTO,
                                           @AuthenticationPrincipal UserDetailsImpl userPrincipal) {
        Account account = modelsMapper.makeAccount(accountDTO);
        account.setUser(userPrincipal.getUser());
        account.setId(id);
        accountsService.updateAccount(account);
        return ResponseEntity.ok("Account updated successfully");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable long id) {
        accountsService.deleteAccount(id);
        return ResponseEntity.ok("Account deleted successfully");
    }

    @ExceptionHandler
    private ResponseEntity<ControllerErrorResponse> handleException(RuntimeException e) {
        ControllerErrorResponse errorResponse = new ControllerErrorResponse(
                e.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
