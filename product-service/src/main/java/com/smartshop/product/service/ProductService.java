package com.smartshop.product.service;

import com.smartshop.product.dto.ProductRequest;
import com.smartshop.product.dto.ProductResponse;
import com.smartshop.product.exception.ProductNotFoundException;
import com.smartshop.product.model.Category;
import com.smartshop.product.model.Product;
import com.smartshop.product.repository.ProductRepository;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  public List<ProductResponse> fetchAllProducts(Pageable pageable){
    List<Product> products = (List<Product>) productRepository.findAll(pageable);
    return products.stream().map(this::convertToProductResponse).toList();
  }

  public ProductResponse fetchProductById(UUID id){

    Product product= findProductById(id);
    return convertToProductResponse(product);
  }

  private Product findProductById(UUID id) {
    return productRepository.findById(id)
        .orElseThrow(() -> new ProductNotFoundException(id + " product not found"));
  }

  public void addProduct(ProductRequest request,String email){
    Product product = convertToProduct(request,email);
    productRepository.save(product);
  }

  public void deleteProduct(UUID id, String email){
    Product product = findProductById(id);
    if(!product.getSellerEmail().equals(email)){
      throw new IllegalCallerException("Product does not belong to the sender");
    }
    productRepository.delete(product);
  }

  public void modifyProduct(ProductRequest request,UUID id, String email){
    Product product = findProductById(id);
    if(!product.getSellerEmail().equals(email)){
      throw new IllegalCallerException("Product does not belong to the sender");
    }
    if(request.getName()!=null) product.setName(request.getName());
    if(request.getQuantity()!=null) product.setQuantity(request.getQuantity());
    if(request.getDescription()!=null) product.setDescription(request.getDescription());
    if(request.getCategory()!=null) product.setCategory(request.getCategory());
    if(request.getPrice()!=null) product.setPrice(request.getPrice());
    productRepository.save(product);
  }

  public List<ProductResponse> fetchProductByName(String name, Pageable pageable){
    List<Product> product=productRepository.findByNameLikeIgnoreCase("%"+name+"%",pageable);
    return product.stream().map(this::convertToProductResponse).toList();
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
