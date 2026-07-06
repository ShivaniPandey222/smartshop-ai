package com.smartshop.order.service;

import com.smartshop.order.dto.OrderRequest;
import com.smartshop.order.dto.OrderRequest.Item;
import com.smartshop.order.dto.ProductResponse;
import com.smartshop.order.event.OrderEventPublisher;
import com.smartshop.order.exception.InsufficientStockException;
import com.smartshop.order.model.Order;
import com.smartshop.order.model.OrderItem;
import com.smartshop.order.model.OrderStatus;
import com.smartshop.order.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final RestClient restClient;
  private final OrderEventPublisher orderEventPublisher;

  public void createOrder(OrderRequest orderRequest, String email){
      Order order = Order.builder()
          .buyerEmail(email)
          .status(OrderStatus.PENDING)
          .createdAt(Instant.now())
          .updatedAt(Instant.now()).build();
    List<OrderItem> orderItems = new ArrayList<>();
    double totalAmount=0;
    for(Item item: orderRequest.items()) {
      ProductResponse productResponse = restClient.get().uri("/api/products/" + item.productId())
          .retrieve().body(
              ProductResponse.class);

      if(productResponse.getQuantity() < item.quantity()){
        throw new InsufficientStockException("Insufficient stock for product "+ productResponse.getName());
      }
      orderItems.add(OrderItem.builder().productName(productResponse.getName())
          .quantity(item.quantity())
          .productId(productResponse.getId())
          .priceAtPurchase(productResponse.getPrice())
          .order(order).build());
      totalAmount += productResponse.getPrice() * item.quantity();
    }
      order.setItems(orderItems);
      order.setTotalAmount(totalAmount);

    orderRepository.save(order);
    orderEventPublisher.publish(order, "CREATED");
  }

  public List<Order> getAllOrders(){
    return orderRepository.findAll();
  }

  public Order getOrderById(UUID id){
    return orderRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Order with id " + id + " not found"));
  }

  public List<Order> getMyOrdersByEmail(String email){
    return orderRepository.findByBuyerEmail(email);
  }


}
