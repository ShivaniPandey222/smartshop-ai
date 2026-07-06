package com.smartshop.order.dto;

import java.util.List;
import java.util.UUID;
import lombok.Getter;

@Getter
public class ProductResponse {

  private UUID id;

  private String name;

  private String description;

  private Double price;

  private List<UUID> category;

  private Integer quantity;

  private String sellerEmail;
}
