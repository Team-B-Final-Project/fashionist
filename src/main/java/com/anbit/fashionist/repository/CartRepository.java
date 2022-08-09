package com.anbit.fashionist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anbit.fashionist.domain.dao.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    
}
