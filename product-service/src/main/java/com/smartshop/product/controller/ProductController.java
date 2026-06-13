package com.smartshop.product.controller;

import com.smartshop.product.dto.ProductRequest;
import com.smartshop.product.dto.ProductResponse;
import com.smartshop.product.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @GetMapping
  public ResponseEntity<List<ProductResponse>> fetchAllProducts(@RequestParam(required = false, name = "name") String name,
                                                                @RequestParam(name= "page", required = false, defaultValue="0") int page,
                                                                @RequestParam(name = "size", required = false, defaultValue="10") int size){
    Sort sort=Sort.by("name").ascending();
    Pageable pageable= PageRequest.of(page,size,sort);
     if(name!=null)
        return ResponseEntity.ok().body(productService.fetchProductByName(name,pageable));
     return ResponseEntity.ok().body(productService.fetchAllProducts(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProductResponse> fetchProductByID(@PathVariable("id") UUID id){
      return ResponseEntity.ok().body(productService.fetchProductById(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteProductById(@PathVariable("id") UUID id, @RequestHeader("X-User-Id") String email){
    productService.deleteProduct(id,email);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @PatchMapping("/{id}")
  public ResponseEntity<?> updateProduct(@RequestBody ProductRequest request,
                                          @PathVariable("id") UUID id,
                                         @RequestHeader("X-User-Id") String email){
    productService.modifyProduct(request, id , email);
    return ResponseEntity.status(HttpStatus.OK).body("Product with id: "+id + "is updated successfully");
  }

}
