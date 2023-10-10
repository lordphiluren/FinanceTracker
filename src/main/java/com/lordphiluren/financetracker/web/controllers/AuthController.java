package com.lordphiluren.financetracker.web.controllers;

import com.lordphiluren.financetracker.web.dto.AuthDTO;
import com.lordphiluren.financetracker.web.mappers.ModelsMapper;
import com.lordphiluren.financetracker.services.RegistrationService;
import com.lordphiluren.financetracker.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final RegistrationService registrationService;
    private final ModelsMapper modelsMapper;
    @Autowired
    public AuthController(RegistrationService registrationService, ModelsMapper modelsMapper, UsersService usersService) {
        this.registrationService = registrationService;
        this.modelsMapper = modelsMapper;
    }
    @PostMapping("/signup")
    public ResponseEntity<HttpStatus> performRegistration(@RequestBody AuthDTO authDTO) {
        registrationService.doRegistration(modelsMapper.makeUser(authDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
