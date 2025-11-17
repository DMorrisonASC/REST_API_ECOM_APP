package com.codewithdaeshaun.store.controllers;


import com.codewithdaeshaun.store.dtos.ProductDto;
import com.codewithdaeshaun.store.mappers.ProductMapper;
import com.codewithdaeshaun.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @GetMapping
    public Iterable<ProductDto> getAllProducts(
            @RequestParam(required = false, name = "categoryId")
            Long id
    ) {
        if (id == null) {
            return productRepository.findAllWithCategory()
                    .stream()
                    .map(productMapper::toDto)
                    .toList();
        }

        else {
            return productRepository.findByCategoryId(id)
                    .stream()
                    .map(productMapper::toDto)
                    .toList();
        }
    }
    @GetMapping("/{id}")  // Different path: /products/{id}
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        // Get single product by ID
        var product = productRepository.findById(id).orElse(null);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productMapper.toDto(product));
    }

}
