package com.anbit.fashionist.repository;


import com.anbit.fashionist.domain.dao.Review;
import com.anbit.fashionist.domain.dao.Transaction;
import com.anbit.fashionist.domain.dao.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByTransaction(Transaction transaction);
    Boolean existsByTransactionAndUser(Transaction transaction, User user);
}
