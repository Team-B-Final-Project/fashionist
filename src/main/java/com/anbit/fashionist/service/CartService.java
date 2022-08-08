package com.anbit.fashionist.service;


import com.anbit.fashionist.domain.dto.CartRequestDTO;
import com.anbit.fashionist.domain.dto.UserRequestDTO;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;

public interface CartService {


    ResponseEntity<?> addCart (CartRequestDTO cartRequestDTO) throws ResourceNotFoundException;

    ResponseEntity<?> editCart (CartRequestDTO cartRequestDTO) throws ResourceNotFoundException;

    public ResponseEntity<?> deleteCart(CartRequestDTO cartRequestDTO, UserRequestDTO userRequestDTO) throws  ResourceNotFoundException;
}
