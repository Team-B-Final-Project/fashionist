package com.anbit.fashionist.repository;

import com.anbit.fashionist.domain.dao.Cart;
import com.anbit.fashionist.domain.dao.Product;
import com.anbit.fashionist.domain.dao.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository <Cart, Long> {
    boolean existsByUser(User user);
    List<Cart> findByUser(User user);
    boolean existsByUserAndProduct(User user, Product product);
}

