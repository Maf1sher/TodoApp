package org.mafisher.backend.controller;

import lombok.AllArgsConstructor;
import org.mafisher.backend.dto.request.CreateCategoryRequest;
import org.mafisher.backend.dto.response.Category;
import org.mafisher.backend.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/list")
    public ResponseEntity<List<Category>> getAllCategories(Principal principal) {
        List<Category> categories = categoryService.getAll(principal);
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/create")
    public ResponseEntity<Category> create(@RequestBody CreateCategoryRequest category, Principal principal) {
        Category savedCategory = categoryService.create(category, principal);
        return ResponseEntity.ok(savedCategory);
    }

    @PostMapping("/edit/{id}")
    public ResponseEntity<Category> edit(
            @PathVariable long id,
            @RequestBody CreateCategoryRequest category,
            Principal principal
    ) {
        Category savedCategory = categoryService.edit(id, category, principal);
        return ResponseEntity.ok(savedCategory);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Category> delete(@PathVariable long id, Principal principal) {
        categoryService.delete(id, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
