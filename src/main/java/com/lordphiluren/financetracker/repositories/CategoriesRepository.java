package com.lordphiluren.financetracker.repositories;

import com.lordphiluren.financetracker.models.Category;
import com.lordphiluren.financetracker.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriesRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByUserAndName(User user, String name);
    Optional<Category> findById(long id);
    List<Category> findAllByUser(User user);
    List<Category> findAllByUserAndIncomeCategoryTrue(User user);
    List<Category> findAllByUserAndIncomeCategoryFalse(User user);
    Optional<Category> findByName(String name);

}
