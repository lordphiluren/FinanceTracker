package com.lordphiluren.financetracker.controllers;

import com.lordphiluren.financetracker.dto.AuthDTO;
import com.lordphiluren.financetracker.factory.ModelsDTOFactory;
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
    private final ModelsDTOFactory modelsDTOFactory;
    @Autowired
    public AuthController(RegistrationService registrationService, ModelsDTOFactory modelsDTOFactory, UsersService usersService) {
        this.registrationService = registrationService;
        this.modelsDTOFactory = modelsDTOFactory;
    }


    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> performRegistration(@RequestBody AuthDTO authDTO) {
        registrationService.doRegistration(modelsDTOFactory.makeUser(authDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
