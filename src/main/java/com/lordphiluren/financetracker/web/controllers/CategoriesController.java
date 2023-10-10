package com.lordphiluren.financetracker.web.controllers;

import com.lordphiluren.financetracker.models.Category;
import com.lordphiluren.financetracker.security.UserDetailsImpl;
import com.lordphiluren.financetracker.services.CategoriesService;
import com.lordphiluren.financetracker.web.dto.CategoryDTO;
import com.lordphiluren.financetracker.web.mappers.ModelsMapper;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {
    private final ModelsMapper modelsMapper;
    private final CategoriesService categoriesService;
    @Autowired
    public CategoriesController(ModelsMapper modelsMapper, CategoriesService categoriesService) {
        this.modelsMapper = modelsMapper;
        this.categoriesService = categoriesService;
    }

    @GetMapping()
    public List<CategoryDTO> getCategories(@AuthenticationPrincipal UserDetailsImpl userPrincipal) {
        return categoriesService.getCategoryByUser(userPrincipal.getUser())
                .stream()
                .map(modelsMapper::makeCategoryDTO)
                .collect(Collectors.toList());
    }
    @PostMapping()
    public ResponseEntity<?> addCategory(@AuthenticationPrincipal UserDetailsImpl userPrincipal,
                                         @RequestBody CategoryDTO categoryDTO) {
        Category category = modelsMapper.makeCategory(categoryDTO);
        category.setUser(userPrincipal.getUser());
        categoriesService.addCategory(category);
        return ResponseEntity.ok("Category successfully added");
    }
}