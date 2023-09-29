//package com.lordphiluren.financetracker.services;
//
//import com.lordphiluren.financetracker.models.User;
//import com.lordphiluren.financetracker.repositories.UsersRepository;
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class RegistrationService {
//    private final PasswordEncoder bCryptPasswordEncoder;
//    private final UsersRepository usersRepository;
//    @Autowired
//    public RegistrationService(PasswordEncoder bCryptPasswordEncoder, UsersRepository usersRepository) {
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//        this.usersRepository = usersRepository;
//    }
//    @Transactional
//    public void doRegistration(User user) {
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        usersRepository.save(user);
//    }
//}
