package com.smartshop.order.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

  @Id
  @GeneratedValue
  private UUID id ;

  private String buyerEmail;
  private Double totalAmount;


  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderItem> items = new ArrayList<>();

  private Instant createdAt;
  private Instant updatedAt;

  public void transitionTo(OrderStatus newStatus){
    if(!this.status.canTransitionTo(newStatus)){
      throw new IllegalStateException("Cannot transition from "+ this.status + "to" + newStatus);
    }

    this.status = newStatus;
    this.updatedAt = Instant.now();
  }

}
