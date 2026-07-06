package com.smartshop.order.repository;

import com.smartshop.order.model.Order;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, UUID> {
   List<Order> findByBuyerEmail(String email);
}
