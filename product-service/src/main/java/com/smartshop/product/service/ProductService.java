package com.smartshop.product.service;

import com.smartshop.product.dto.ProductRequest;
import com.smartshop.product.dto.ProductResponse;
import com.smartshop.product.exception.ProductNotFoundException;
import com.smartshop.product.model.Category;
import com.smartshop.product.model.Product;
import com.smartshop.product.repository.ProductRepository;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;


  public ProductResponse fetchProductById(UUID id){

    Product product= productRepository.findById(id).orElseThrow(()->new ProductNotFoundException(id + " product not found"));
    return convertToProductResponse(product);
  }

  public void addProduct(ProductRequest request,String email){
    Product product = convertToProduct(request,email);
    productRepository.save(product);
  }

  private ProductResponse convertToProductResponse(Product product){
    return ProductResponse.builder()
        .id(product.getId())
        .name(product.getName())
        .price(product.getPrice())
        .description(product.getDescription())
        .category(product.getCategory().stream().map(Category::getId).toList())
        .sellerEmail(product.getSellerEmail())
        .quantity(product.getQuantity())
        .build();
  }

  private Product convertToProduct(ProductRequest request,String email){
      return Product.builder()
        .name(request.getName())
        .description(request.getDescription())
        .price(request.getPrice())
        .category(request.getCategory())
        .sellerEmail(email)
        .quantity(request.getQuantity())
        .createdAt(Instant.now())
        .updatedAt(Instant.now())
        .build();
  }

}
