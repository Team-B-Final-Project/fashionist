package com.anbit.fashionist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anbit.fashionist.domain.dao.ProductTransaction;
import com.anbit.fashionist.domain.dao.Transaction;

@Repository
public interface ProductTransactionRepository extends JpaRepository<ProductTransaction, Long> {
    List<ProductTransaction> findByTransaction(Transaction transaction);
}
