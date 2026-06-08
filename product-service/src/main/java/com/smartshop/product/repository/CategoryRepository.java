package com.smartshop.product.repository;

import com.smartshop.product.model.Category;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,UUID> {


}
