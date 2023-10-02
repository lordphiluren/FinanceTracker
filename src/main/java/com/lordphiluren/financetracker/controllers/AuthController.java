package com.lordphiluren.financetracker.controllers;

import com.lordphiluren.financetracker.dto.AuthDTO;
import com.lordphiluren.financetracker.factory.ModelsDTOFactory;
import com.lordphiluren.financetracker.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final RegistrationService registrationService;
    private final AuthenticationManager authenticationManager;
    private final ModelsDTOFactory modelsDTOFactory;
    @Autowired
    public AuthController(RegistrationService registrationService, AuthenticationManager authenticationManager, ModelsDTOFactory modelsDTOFactory) {
        this.registrationService = registrationService;
        this.authenticationManager = authenticationManager;
        this.modelsDTOFactory = modelsDTOFactory;
    }

    @PostMapping("/login")
    public ResponseEntity<HttpStatus> performLogin(@RequestBody AuthDTO authDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authDTO.getUsername(), authDTO.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> performRegistration(@RequestBody AuthDTO authDTO) {
        registrationService.doRegistration(modelsDTOFactory.makeUser(authDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
