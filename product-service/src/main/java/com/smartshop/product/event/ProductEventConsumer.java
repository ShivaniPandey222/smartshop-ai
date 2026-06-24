package com.smartshop.product.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductEventConsumer {
  @KafkaListener(topics = "product-events", groupId = "product-service-group")
  public void handleProductEvent(ProductUpdatedEvent event) {
    log.info("Received event: {} for product {} ({})",
        event.eventType(), event.name(), event.productId());

  }
}
