package com.lordphiluren.financetracker.web.controllers;

import com.lordphiluren.financetracker.models.Category;
import com.lordphiluren.financetracker.security.UserDetailsImpl;
import com.lordphiluren.financetracker.services.CategoriesService;
import com.lordphiluren.financetracker.utils.exceptions.ControllerErrorResponse;
import com.lordphiluren.financetracker.web.dto.CategoryDTO;
import com.lordphiluren.financetracker.web.mappers.ModelsMapper;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @GetMapping("/{type}")
    public ResponseEntity<?> getCategories(@PathVariable String type,
                                           @AuthenticationPrincipal UserDetailsImpl userPrincipal) {
        if(type.equals("incomes")) {
            List<CategoryDTO> categoryDTOS = categoriesService.getIncomeCategories(userPrincipal.getUser())
                    .stream()
                    .map(modelsMapper::makeCategoryDTO)
                    .toList();
            return new ResponseEntity<>(categoryDTOS, HttpStatus.OK);
        }
        else if(type.equals("expenses")) {
            List<CategoryDTO> categoryDTOS = categoriesService.getExpenseCategories(userPrincipal.getUser())
                    .stream()
                    .map(modelsMapper::makeCategoryDTO)
                    .toList();
            return new ResponseEntity<>(categoryDTOS, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Invalid value of type parameter", HttpStatus.BAD_REQUEST);
        }

    }
    @PostMapping("/{type}")
    public ResponseEntity<?> addCategory(@PathVariable String type,
                                         @AuthenticationPrincipal UserDetailsImpl userPrincipal,
                                         @RequestBody CategoryDTO categoryDTO) {
        Category category = modelsMapper.makeCategory(categoryDTO);
        category.setUser(userPrincipal.getUser());
        if (type.equals("incomes")) {
            category.setIncomeCategory(true);
        }
        else if (type.equals("expenses")) {
            category.setIncomeCategory(false);
        }
        else {
            return new ResponseEntity<>("Invalid value of type parameter", HttpStatus.BAD_REQUEST);
        }
        categoriesService.addCategory(category);
        return ResponseEntity.ok("Category successfully added");
    }
    @GetMapping("/{type}/{name}")
    public ResponseEntity<?> getCategoryByName(@PathVariable String type, @PathVariable String name) {
        if (type.equals("incomes") || type.equals("expenses")) {
            CategoryDTO categoryDTO = modelsMapper.makeCategoryDTO(categoriesService.getCategoryByName(name));
            return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
        }
        else return new ResponseEntity<>("Invalid value of type parameter", HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    private ResponseEntity<ControllerErrorResponse> handleException(RuntimeException e) {
        ControllerErrorResponse errorResponse = new ControllerErrorResponse(
                e.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}