package com.anbit.fashionist.repository;

import com.anbit.fashionist.domain.dao.Category;
import com.anbit.fashionist.domain.dao.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByName(String keyword, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

    Page<Product> findByCategoryAndNameContainingIgnoreCase(Category category ,String keyword, Pageable pageable);

    Optional<Product> findById(Long id);
}
