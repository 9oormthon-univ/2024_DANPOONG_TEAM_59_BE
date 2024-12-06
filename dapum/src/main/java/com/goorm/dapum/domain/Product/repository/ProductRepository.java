package com.goorm.dapum.domain.Product.repository;

import com.goorm.dapum.domain.Product.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
