package com.codewithdaeshaun.store.repositories;

import com.codewithdaeshaun.store.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
}