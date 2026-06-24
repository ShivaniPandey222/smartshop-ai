package com.smartshop.product.event;

import com.smartshop.product.model.ProductReadModel;
import com.smartshop.product.repository.ProductReadRepository;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ProductReadModelProjector {

  private final ProductReadRepository productReadRepository;

  @KafkaListener(topics = "product-events" , groupId = "read-product-projector")
  public void project(ProductUpdatedEvent event){
      if(event.eventType().equals("DELETED")){
        productReadRepository.deleteById(event.productId().toString());
        log.info("Removed {} from read model", event.productId());
        return;
      }
    ProductReadModel productReadModel= ProductReadModel.builder()
        .id(event.productId().toString())
        .name(event.name())
        .price(event.price())
        .stock(event.stock())
        .sellerEmail(event.sellerEmail())
        .inStock(event.stock()>0)
        .categoryName(event.categoryNames())
        .lastUpdated(Instant.now())
        .build();

      productReadRepository.save(productReadModel);
    log.info("Projected {} into mongodb ({})", event.name(), event.eventType());
  }
}
