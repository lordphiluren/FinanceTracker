package com.lordphiluren.financetracker.repositories;

import com.lordphiluren.financetracker.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String name);
    Optional<User> findById(long id);
}
