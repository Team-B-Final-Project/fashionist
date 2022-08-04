package com.anbit.fashionist.repository;

import com.anbit.fashionist.domain.dao.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsById(Long id);
}
