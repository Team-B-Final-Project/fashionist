package com.anbit.fashionist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anbit.fashionist.domain.dao.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    boolean existsByUserUsername(String username);
}
