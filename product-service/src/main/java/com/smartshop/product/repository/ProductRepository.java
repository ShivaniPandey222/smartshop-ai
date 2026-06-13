package com.smartshop.product.repository;

import com.smartshop.product.model.Product;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, UUID> {
     List<Product> findByNameLikeIgnoreCase(String name, Pageable pageable);
}
