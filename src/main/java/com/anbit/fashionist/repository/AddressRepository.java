package com.anbit.fashionist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anbit.fashionist.domain.dao.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    
}
