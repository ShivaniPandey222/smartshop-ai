package com.smartshop.order.dto;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

public record OrderRequest(@Valid List<Item> items) {
  public record Item(
      UUID productId,
      int quantity
  ){}
}
