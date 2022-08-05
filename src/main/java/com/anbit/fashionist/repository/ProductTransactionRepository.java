package com.anbit.fashionist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anbit.fashionist.domain.dao.ProductTransaction;

public interface ProductTransactionRepository extends JpaRepository<ProductTransaction, Long> {
    
}
