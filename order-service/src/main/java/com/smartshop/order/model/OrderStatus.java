package com.smartshop.order.model;

public enum OrderStatus {

    PENDING,
    PAYMENT_PROCESSING,
    CONFIRMED,
    SHIPPED,
    DELIVERED,
    CANCELLED,
    REPLACEMENT_INITIATED,
    REFUND_INITIATED,
    REFUNDED,
    REPLACED,
    COMPLETED,
    FAILED;

  public boolean canTransitionTo(OrderStatus next){
      return switch (this) {
        case PENDING -> next == PAYMENT_PROCESSING || next == FAILED ;
        case PAYMENT_PROCESSING -> next == CONFIRMED || next == FAILED;
        case CONFIRMED -> next == SHIPPED || next == CANCELLED;
        case SHIPPED -> next == DELIVERED || next == CANCELLED;
        case DELIVERED -> next == REPLACEMENT_INITIATED || next ==REFUND_INITIATED || next == COMPLETED;
        case REPLACEMENT_INITIATED -> next == REPLACED;
        case REFUNDED , REPLACED -> next == COMPLETED;
        case CANCELLED -> next == REFUND_INITIATED;
        case REFUND_INITIATED -> next == REFUNDED;
        case COMPLETED, FAILED -> false;
      };
  }
}
