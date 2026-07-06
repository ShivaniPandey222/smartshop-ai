package com.smartshop.order.event;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record OrderCreatedEvent (

  UUID orderId,
  String buyerEmail,
  double totalAmount,
  Instant createdAt,
  Instant updatedAt,
  List<OrderItemDetails> orderItems,
  String eventType
){
  public record OrderItemDetails(
       UUID productId,
       String productName,
       Double priceAtPurchase,
       int quantity
  ){}
};
