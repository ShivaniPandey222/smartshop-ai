package com.smartshop.product.controller;

import com.smartshop.product.dto.CategoryRequest;
import com.smartshop.product.model.Category;
import com.smartshop.product.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
@AllArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;
  @PostMapping
  ResponseEntity<String> addCategory(@Valid @RequestBody CategoryRequest request){
    categoryService.addCategory(request);
    return ResponseEntity.ok().body("Catgeory added");
  }
}
