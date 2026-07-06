package com.smartshop.order.controller;

import com.smartshop.order.dto.OrderRequest;
import com.smartshop.order.service.OrderService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
public class OrderController {

  private final OrderService orderService;

  @PostMapping
  public ResponseEntity addOrder(
      @Valid @RequestBody OrderRequest request,
      @RequestHeader("X-User-Id") String buyerEmail)
  {
      orderService.createOrder(request,buyerEmail);
      return ResponseEntity.status(HttpStatus.CREATED).body("Order created successfully");
  }

  @GetMapping
  public ResponseEntity getAllOrders(){
    return ResponseEntity.status(HttpStatus.OK).body(orderService.getAllOrders());
  }

  @GetMapping("/{id}")
  public ResponseEntity getOrderById(@PathVariable(name = "id") UUID id){
    return ResponseEntity.status(HttpStatus.OK).body(orderService.getOrderById(id));
  }

  @GetMapping("/my")
  public ResponseEntity getMyOrders(@RequestHeader("X-User-Id") String buyerEmail){

    return ResponseEntity.status(HttpStatus.OK).body(orderService.getMyOrdersByEmail(buyerEmail));
  }
}
