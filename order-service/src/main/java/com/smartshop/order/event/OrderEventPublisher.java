package com.smartshop.order.event;


import com.smartshop.order.event.OrderCreatedEvent.OrderItemDetails;
import com.smartshop.order.model.Order;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventPublisher {
      private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

      private static String TOPIC="order-event";


      public void publish(Order order, String eventType){

            List<OrderItemDetails> orderItemDetails = order.getItems().stream()
                .map(item -> new OrderItemDetails(
                    item.getProductId(),
                    item.getProductName(),
                    item.getPriceAtPurchase(),
                    item.getQuantity()
                )).toList();

            OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent(
                order.getId(),
                order.getBuyerEmail(),
                order.getTotalAmount(),
                order.getCreatedAt(),
                order.getUpdatedAt(),
                orderItemDetails,
                eventType
            );

            kafkaTemplate.send(TOPIC,orderCreatedEvent.orderId().toString(), orderCreatedEvent);
      }
}
