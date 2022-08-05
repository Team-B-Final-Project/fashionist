package com.anbit.fashionist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anbit.fashionist.domain.dao.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    
}
