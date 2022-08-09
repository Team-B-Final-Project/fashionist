package com.anbit.fashionist.service;


import com.anbit.fashionist.domain.dto.AddCartRequestDTO;
import com.anbit.fashionist.domain.dto.EditCartTotalItemRequestDTO;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;

public interface CartService {

    ResponseEntity<?> addCart (AddCartRequestDTO requestDTO) throws ResourceNotFoundException;

    ResponseEntity<?> editCartTotalItem (EditCartTotalItemRequestDTO requestDTO) throws ResourceNotFoundException;

    ResponseEntity<?> deleteCart(Long id) throws  ResourceNotFoundException;
}
