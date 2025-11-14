package com.codewithdaeshaun.store.repositories;

import com.codewithdaeshaun.store.entities.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Byte> {
}