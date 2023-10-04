package com.lordphiluren.financetracker.services;

import com.lordphiluren.financetracker.models.Category;
import com.lordphiluren.financetracker.models.User;
import com.lordphiluren.financetracker.repositories.CategoriesRepository;
import com.lordphiluren.financetracker.utils.exceptions.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoriesService {
    private final CategoriesRepository categoriesRepository;
    @Autowired
    public CategoriesService(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    public Category getCategoryByUserAndName(User user, String name) {
        Optional<Category> category = categoriesRepository.findByUserAndName(user, name);
        if(category.isEmpty())
            throw new CategoryNotFoundException("Category not found");
        return category.get();
    }

}