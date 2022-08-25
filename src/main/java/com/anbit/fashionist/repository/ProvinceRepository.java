package com.anbit.fashionist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anbit.fashionist.domain.dao.Province;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer> {
    List<Province> findByNameContainingIgnoreCase(String name);
}
