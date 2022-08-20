package com.anbit.fashionist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anbit.fashionist.domain.dao.District;

public interface DistrictRepository extends JpaRepository<District, Integer> {
    List<District> findByNameContainingIgnoreCase(String name);

    List<District> findByRegencyId(Integer regId);

    List<District> findByNameContainingIgnoreCaseAndRegencyId(String name, Integer regId);
}
