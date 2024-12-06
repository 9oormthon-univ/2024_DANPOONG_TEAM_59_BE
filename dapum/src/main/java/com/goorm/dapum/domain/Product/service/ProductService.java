package com.goorm.dapum.domain.Product.service;

import com.goorm.dapum.domain.Product.dto.ProductRequest;
import com.goorm.dapum.domain.Product.entity.Product;
import com.goorm.dapum.domain.Product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    @Autowired
    private final ProductRepository productRepository;

    // 상품 추가
    public void addProduct(ProductRequest request) {
        Product product = new Product(request);
        productRepository.save(product);
    }

    // 상품 목록 조회
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // 상품 상세 정보 조회
    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다."));
    }

    // 상품 수정
    public void updateProduct(Long productId, ProductRequest request) {
        Product product = getProductById(productId);
        product.update(request);
        productRepository.save(product);
    }

    // 상품 삭제
    public void deleteProduct(Long productId) {
        Product product = getProductById(productId);
        productRepository.delete(product);
    }
}
