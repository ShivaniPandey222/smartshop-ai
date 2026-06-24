package com.smartshop.product.event;

import com.smartshop.product.model.Category;
import com.smartshop.product.model.Product;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductEventProducer {

        private final KafkaTemplate<String, ProductUpdatedEvent> kafkaTemplate;

        private static final String TOPIC="product-events";

        public void publishProductUpdated(Product product, String eventType){
          ProductUpdatedEvent event = new ProductUpdatedEvent(
              product.getId(),
              product.getName(),
              product.getDescription(),
              product.getPrice(),
              product.getQuantity(),
              product.getSellerEmail(),
              product.getCategory().stream().map(Category::getName).collect(Collectors.toSet()),
              eventType
          );

          // key = productId → ensures all events for same product go to same partition (ordering)
          kafkaTemplate.send(TOPIC, product.getId().toString(), event);
        }
}
