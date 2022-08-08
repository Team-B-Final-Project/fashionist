package com.anbit.fashionist.repository;

import com.anbit.fashionist.domain.dao.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository <Cart, Long> {
    Boolean existsByCartId(Long id);
}
