package com.dhart.backend.controller;

import com.dhart.backend.exceptions.NotFoundException;
import com.dhart.backend.exceptions.RegisteredResourceException;
import com.dhart.backend.model.dto.CategoryDTO;
import com.dhart.backend.service.CategoryService;
import com.dhart.backend.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "*")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    S3Service s3Service;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping
    public List<CategoryDTO> findAllCategories() {
        return categoryService.findAllCategories();
    }

    @PostMapping
    public ResponseEntity<?> saveCategory(@RequestBody CategoryDTO categoryDTO)
            throws RegisteredResourceException {

        categoryDTO.setImageUrl(s3Service.getObjectUrl(categoryDTO.getImagePath()));
        categoryService.saveCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("successfully registered category");
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findCategoryById(@PathVariable Long id) throws NotFoundException {
        Optional<CategoryDTO> category = categoryService.findCategoryById(id);
        if (category.isPresent()) {
            return ResponseEntity.ok(category.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/byName/{text}")
    public List<CategoryDTO> findCategoryByname(@PathVariable String text) {
        return categoryService.findCategoryByName(text);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) throws NotFoundException {
        Optional<CategoryDTO> category = categoryService.findCategoryById(id);
        if(category.isPresent()){
            categoryService.deleteCategory(id);
            return ResponseEntity.ok("Category is successfully removed");
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found");
        }
    }

}
