package com.anbit.fashionist.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anbit.fashionist.constant.EShipping;
import com.anbit.fashionist.domain.dao.Shipping;

public interface ShippingRepository extends JpaRepository<Shipping, Integer> {
    Optional<Shipping> findByName(EShipping name);
}
