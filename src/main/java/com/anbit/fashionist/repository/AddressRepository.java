package com.anbit.fashionist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anbit.fashionist.domain.dao.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
    
}
