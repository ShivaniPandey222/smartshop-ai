package com.smartshop.product.controller;

import com.smartshop.product.dto.ProductRequest;
import com.smartshop.product.dto.ProductResponse;
import com.smartshop.product.service.ProductService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

  private final ProductService productService;

  @PostMapping
  public ResponseEntity<?> addProduct(@Valid @RequestBody ProductRequest request,
      @RequestHeader("X-User-Id") String email){
    productService.addProduct(request,email);
    return ResponseEntity.status(HttpStatus.CREATED).body("Product added");
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductResponse> fetchProducts(@PathVariable("id") UUID id){
      return ResponseEntity.ok().body(productService.fetchProductById(id));
  }
}
