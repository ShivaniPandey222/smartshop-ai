package com.smartshop.product.dto;

import com.smartshop.product.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.Getter;

@Getter
public class ProductRequest {

  @NotBlank
  private String name;

  @NotBlank
  private String description;

  @NotNull
  private Double price;

  private Set<Category> category;

  @NotNull
  private Integer quantity;

}
