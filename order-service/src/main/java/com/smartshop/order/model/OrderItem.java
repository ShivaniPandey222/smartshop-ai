package com.smartshop.order.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_items")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {
      @Id
      @GeneratedValue
      private UUID id;

      @ManyToOne
      @JoinColumn(name = "order_id")
      @JsonIgnore
      private Order order;

      private UUID productId;
      private String productName;
      private Double priceAtPurchase;
      private int quantity;

}
