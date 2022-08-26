package com.anbit.fashionist.repository;

import com.anbit.fashionist.domain.dao.Category;
import com.anbit.fashionist.domain.dao.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByName(String keyword, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE UPPER(p.name) LIKE UPPER(concat('%', :keyword, '%'))" 
        + " AND (:category is null or p.category = :category)")
        // + " AND (:locations is null or UPPER(p.store.address.village.district.regency.name) in UPPER(:locations))")
        // + " AND (:minPrice is null or p.price >= :minPrice)" 
        // + " AND (:maxPrice is null or p.price <= :maxPrice)")
    Page<Product> searchProduct(
        @Param("category") Category category ,
        @Param("keyword") String keyword, 
        // @Param("locations") String locations, 
        // @Param("minPrice") Float minPrice, 
        // @Param("maxPrice") Float maxPrice, 
        Pageable pageable);

    Optional<Product> findById(Long id);
}
