package com.smartshop.product.model;

import jakarta.persistence.Id;
import java.time.Instant;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "product_read_model")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductReadModel {

  @Id
  private String id;

  private String name;
  private Double price;
  private int stock;
  private String sellerEmail;
  private boolean inStock;
  private Instant lastUpdated;
  private Set<String> categoryName;
}
