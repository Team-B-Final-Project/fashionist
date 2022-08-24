package com.anbit.fashionist.repository;

import com.anbit.fashionist.domain.dao.Product;
import com.anbit.fashionist.domain.dao.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Boolean existsByProductIdAndUserId(Long productId, Long userId);
    List<Review> findByProduct(Product product);
}
