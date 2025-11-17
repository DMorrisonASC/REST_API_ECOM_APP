package com.codewithdaeshaun.store.repositories;

import com.codewithdaeshaun.store.entities.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @EntityGraph(attributePaths = "category")
    List<Product> findByCategoryId(Long categoryId);

//    @EntityGraph(attributePaths = "category")
//    @Query("SELECT p From Product p JOIN FETCH p.category")
//    Or
    @Query("SELECT p From Product p JOIN FETCH p.category")
    List<Product> findAllWithCategory();
}