package com.anbit.fashionist.repository;


import com.anbit.fashionist.domain.dao.Product;
import com.anbit.fashionist.domain.dao.User;
import com.anbit.fashionist.domain.dao.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findByUser(User user);

    Boolean existsByUserAndProduct(User user, Product product);
}

