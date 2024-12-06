package com.goorm.dapum.application.controller.product;

import com.goorm.dapum.domain.Product.dto.ProductRequest;
import com.goorm.dapum.domain.Product.entity.Product;
import com.goorm.dapum.domain.Product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @Operation(summary = "상품 추가")
    public ResponseEntity<Void> addProduct(@RequestBody ProductRequest request) {
        productService.addProduct(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "상품 목록 조회")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{productId}")
    @Operation(summary = "상품 상세 정보 조회")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        Product product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/{productId}")
    @Operation(summary = "상품 수정")
    public ResponseEntity<Void> updateProduct(@PathVariable Long productId, @RequestBody ProductRequest request) {
        productService.updateProduct(productId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{productId}")
    @Operation(summary = "상품 삭제")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }
}

