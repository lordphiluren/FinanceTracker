package com.lordphiluren.financetracker.services;

import com.lordphiluren.financetracker.utils.exceptions.UserNotFoundException;
import com.lordphiluren.financetracker.models.User;
import com.lordphiluren.financetracker.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    @Autowired
    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
    public User getUserById(long id) {
        return usersRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
