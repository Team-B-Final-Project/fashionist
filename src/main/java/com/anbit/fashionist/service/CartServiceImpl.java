package com.anbit.fashionist.service;

import com.anbit.fashionist.domain.dto.CartRequestDTO;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    CartRepository cartRepository;

    @Autowired

    @Override
    public ResponseEntity<?> addCart(CartRequestDTO cartRequestDTO) throws ResourceNotFoundException {
        try{

        }
        return null;
    }

    @Override
    public ResponseEntity<?> editCart(CartRequestDTO cartRequestDTO) throws ResourceNotFoundException {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteCart(CartRequestDTO cartRequestDTO) throws  ResourceNotFoundException {
        return null;
    }

}
