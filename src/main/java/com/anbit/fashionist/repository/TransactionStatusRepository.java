package com.anbit.fashionist.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anbit.fashionist.constant.ETransactionStatus;
import com.anbit.fashionist.domain.dao.TransactionStatus;

@Repository
public interface TransactionStatusRepository extends JpaRepository<TransactionStatus, Integer> {
    Optional<TransactionStatus> findByName(ETransactionStatus name);
}
