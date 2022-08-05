package com.anbit.fashionist.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anbit.fashionist.constant.EPayment;
import com.anbit.fashionist.domain.dao.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    Optional<Payment> findByName(EPayment name);
}
