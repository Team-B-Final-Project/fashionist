package com.anbit.fashionist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anbit.fashionist.domain.dao.Regency;

@Repository
public interface RegencyRepository extends JpaRepository<Regency, Integer> {
    List<Regency> findByNameContainingIgnoreCase(String name);
    
    List<Regency> findByProvinceId(Integer provinceId);

    List<Regency> findByNameContainingIgnoreCaseAndProvinceId(String name, Integer provinceId);
}
