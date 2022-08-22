package com.anbit.fashionist.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anbit.fashionist.constant.ECategory;
import com.anbit.fashionist.domain.dao.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findByName(ECategory name);
}
