package com.codewithdaeshaun.store.repositories;

import com.codewithdaeshaun.store.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}