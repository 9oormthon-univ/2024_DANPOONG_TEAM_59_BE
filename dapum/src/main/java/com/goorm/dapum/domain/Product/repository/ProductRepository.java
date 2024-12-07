package com.goorm.dapum.domain.Product.repository;

import com.goorm.dapum.domain.Product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
