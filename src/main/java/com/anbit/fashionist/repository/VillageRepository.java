package com.anbit.fashionist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anbit.fashionist.domain.dao.Village;

public interface VillageRepository extends JpaRepository<Village, Long> {
    List<Village> findByNameContainingIgnoreCase(String name);

    List<Village> findByDistrictId(Integer districtId);
    
    List<Village> findByNameContainingIgnoreCaseAndDistrictId(String name, Integer districtId);
}
