package com.smartshop.product.controller;

import com.smartshop.product.model.ProductReadModel;
import com.smartshop.product.repository.ProductReadRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products/search")
public class ProductSearchController {

  private final ProductReadRepository productReadRepository;

  @GetMapping
  public List<ProductReadModel> search(
      @RequestParam(name = "name", required = false) String name,
      @RequestParam(name = "page", required = false , defaultValue = "0") int page,
      @RequestParam(name = "size", required = false, defaultValue = "10") int size
  ){
    Pageable pageable = PageRequest.of(page,size, Sort.by("name").ascending());
    if(!StringUtils.isEmpty(name))
      return productReadRepository.findByNameContainingIgnoreCase(name,pageable);

    return (List<ProductReadModel>) productReadRepository.findAll(pageable);
  }

}
