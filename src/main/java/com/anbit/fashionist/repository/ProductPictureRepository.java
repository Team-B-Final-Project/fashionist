package com.anbit.fashionist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anbit.fashionist.domain.dao.ProductPicture;

public interface ProductPictureRepository extends JpaRepository<ProductPicture, Long> {
    
}
