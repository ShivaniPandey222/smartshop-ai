package com.smartshop.product.dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductResponse implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private UUID id;

  private String name;

  private String description;

  private Double price;

  private List<UUID> category;

  private Integer quantity;

  private String sellerEmail;

}
