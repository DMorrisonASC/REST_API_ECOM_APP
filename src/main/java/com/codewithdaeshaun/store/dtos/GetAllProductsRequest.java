package com.codewithdaeshaun.store.dtos;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class GetAllProductsRequest {
    private Long categoryId;      // Filter by category
    private String searchTerm;    // Search in name/description
    private BigDecimal minPrice;  // Price range filtering
    private BigDecimal maxPrice;
    private String sortBy;        // Sorting: "name", "price", "createdAt"
    private String sortOrder;     // "asc" or "desc"
    private Integer page;         // For pagination
    private Integer size;         // Page size
}