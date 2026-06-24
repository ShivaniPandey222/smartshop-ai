package com.smartshop.product.repository;

import com.smartshop.product.model.ProductReadModel;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductReadRepository extends MongoRepository<ProductReadModel, String> {

  List<ProductReadModel> findByNameContainingIgnoreCase(String keyword, Pageable pageable);


}
