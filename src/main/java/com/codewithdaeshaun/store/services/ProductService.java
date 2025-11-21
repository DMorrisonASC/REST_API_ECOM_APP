package com.codewithdaeshaun.store.services;

import com.codewithdaeshaun.store.dtos.CreateProductRequest;
import com.codewithdaeshaun.store.dtos.GetAllProductsRequest;
import com.codewithdaeshaun.store.dtos.ProductDto;
import com.codewithdaeshaun.store.dtos.UpdateProductRequest;
import com.codewithdaeshaun.store.entities.Category;
import com.codewithdaeshaun.store.entities.Product;
import com.codewithdaeshaun.store.exceptions.InvalidPriceException;
import com.codewithdaeshaun.store.exceptions.ProductAlreadyExistsException;
import com.codewithdaeshaun.store.exceptions.ProductNotFoundException;
import com.codewithdaeshaun.store.mappers.ProductMapper;
import com.codewithdaeshaun.store.mappers.UserMapper;
import com.codewithdaeshaun.store.repositories.CategoryRepository;
import com.codewithdaeshaun.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;
    private final UserMapper userMapper;

    public Iterable<ProductDto> getAllProducts(GetAllProductsRequest filters) {
        // The filtering logic
        if (filters.getCategoryId() == null) {
            return productRepository.findAllWithCategory()
                    .stream()
                    .map(productMapper::toDto)
                    .toList();
        } else {
            return productRepository.findByCategoryId(filters.getCategoryId())
                    .stream()
                    .map(productMapper::toDto)
                    .toList();
        }
    }

    public ProductDto getProduct(Long id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        return productMapper.toDto(existingProduct);
    }


    public ProductDto createProduct(CreateProductRequest request) {
        if (request.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidPriceException("Price cannot be negative");
        }

        if (productRepository.findByName(request.getName()).isPresent()) {
            throw new ProductAlreadyExistsException("Product name must be unique");
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        Product newProduct = productMapper.toEntity(request);
        newProduct.setCategory(category);
        Product savedProduct = productRepository.save(newProduct);
        return productMapper.toDto(savedProduct);
    }

    public ProductDto updateProduct(UpdateProductRequest updateRequest, Long id) {
        Product oldProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        productMapper.update(updateRequest, oldProduct);
        productRepository.save(oldProduct);

        return productMapper.toDto(oldProduct);
    }

    public ProductDto deleteProduct(Long id) {
        Product trash = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        productRepository.delete(trash);
        return productMapper.toDto(trash);
    }



}