package com.codewithdaeshaun.store.controllers;


import com.codewithdaeshaun.store.dtos.CreateProductRequest;
import com.codewithdaeshaun.store.dtos.GetAllProductsRequest;
import com.codewithdaeshaun.store.dtos.ProductDto;
import com.codewithdaeshaun.store.dtos.UpdateProductRequest;
import com.codewithdaeshaun.store.mappers.ProductMapper;
import com.codewithdaeshaun.store.repositories.ProductRepository;
import com.codewithdaeshaun.store.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public Iterable<ProductDto> getAllProducts(@ModelAttribute GetAllProductsRequest filters) {
        return productService.getAllProducts(filters);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProduct(id));
    }

    @PostMapping()
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody CreateProductRequest request,
            UriComponentsBuilder uriBuilder) {

        ProductDto productDto = productService.createProduct(request);

        var uri = uriBuilder.path("/products/{id}").buildAndExpand(productDto.getId()).toUri();
        return ResponseEntity.created(uri).body(productDto);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(
            @PathVariable Long id,
            @RequestBody UpdateProductRequest request) {

        ProductDto updatedProduct = productService.updateProduct(request, id);

        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDto> deleteProduct(
            @PathVariable Long id)
    {
        ProductDto deletedProduct = productService.deleteProduct(id);
        return ResponseEntity.ok(deletedProduct);
    }

}
