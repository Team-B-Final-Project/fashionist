package com.anbit.fashionist.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.anbit.fashionist.constant.EErrorCode;
import com.anbit.fashionist.constant.EPayment;
import com.anbit.fashionist.constant.EShipping;
import com.anbit.fashionist.constant.ETransactionStatus;
import com.anbit.fashionist.domain.common.UserDetailsImpl;
import com.anbit.fashionist.domain.dao.Cart;
import com.anbit.fashionist.domain.dao.Payment;
import com.anbit.fashionist.domain.dao.ProductTransaction;
import com.anbit.fashionist.domain.dao.Shipping;
import com.anbit.fashionist.domain.dao.Transaction;
import com.anbit.fashionist.domain.dao.User;
import com.anbit.fashionist.domain.dto.CreateTransactionRequestDTO;
import com.anbit.fashionist.domain.dto.TransactionHistoriesResponseDTO;
import com.anbit.fashionist.domain.dto.TransactionHistoryResponseDTO;
import com.anbit.fashionist.handler.ResponseHandler;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.repository.AddressRepository;
import com.anbit.fashionist.repository.CartRepository;
import com.anbit.fashionist.repository.PaymentRepository;
import com.anbit.fashionist.repository.ProductTransactionRepository;
import com.anbit.fashionist.repository.ShippingRepository;
import com.anbit.fashionist.repository.TransactionRepository;
import com.anbit.fashionist.repository.TransactionStatusRepository;
import com.anbit.fashionist.repository.UserRepository;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductTransactionRepository productTransactionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    TransactionStatusRepository transactionStatusRepository;

    @Autowired
    ShippingRepository shippingRepository;

    
    @Override
    public ResponseEntity<?> createTransaction(CreateTransactionRequestDTO requestDTO) throws ResourceNotFoundException {
        try {
            List<Cart> cartList = new ArrayList<>();
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.getReferenceById(userDetails.getId());
            Payment payment = paymentRepository.findByName(EPayment.valueOf(requestDTO.getPaymentMethod().toUpperCase())).orElseThrow(() -> new ResourceNotFoundException("Payment not found!"));
            List<Float> productPrices = new ArrayList<>();
            List<Integer> itemUnits = new ArrayList<>();
            requestDTO.getCartShipping().keySet().forEach(cartId -> {
                try {
                    Cart cartPrice = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart with ID" + cartId + " not found!"));
                    productPrices.add(cartPrice.getTotalPrice());
                    itemUnits.add(cartPrice.getItemUnit());
                } catch (ResourceNotFoundException e) {
                    e.printStackTrace();
                }
            });
            Double totalPrice = productPrices.stream().mapToDouble(map -> map.doubleValue()).sum();
            Integer totalItemUnit = itemUnits.stream().mapToInt(map -> map.intValue()).sum();
            Transaction transaction = Transaction.builder()
                .user(user)
                .sendAddress(addressRepository.getReferenceById(requestDTO.getSendAddressId()))
                .transactionStatus(transactionStatusRepository.findByName(ETransactionStatus.WAITING_PAYMENT).get())
                .payment(payment)
                .totalItemUnit(totalItemUnit)
                .totalPrice(totalPrice.floatValue())
                .build();
            Transaction newTransaction = transactionRepository.save(transaction);

            for (Long cartId : requestDTO.getCartShipping().keySet()){
                Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart with ID" + cartId + "doesn't exist"));
                cartList.add(cart);
                Shipping shipping = shippingRepository.findByName(EShipping.valueOf(requestDTO.getCartShipping().get(cartId))).orElseThrow(() -> new ResourceNotFoundException("Shipping not found!"));
                ProductTransaction productTransaction = ProductTransaction.builder()
                    .product(cart.getProduct())
                    .transaction(newTransaction)
                    .itemUnit(cart.getItemUnit())
                    .totalPrice(cart.getTotalPrice())
                    .shipping(shipping)
                    .build();
                productTransactionRepository.save(productTransaction);
            }
            return ResponseHandler.generateSuccessResponse(HttpStatus.OK, ZonedDateTime.now(), "New transaction is created successfully!", null);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateErrorResponse(HttpStatus.NOT_FOUND, ZonedDateTime.now(), e.getMessage(), EErrorCode.MISSING_PARAM.getCode());
        }
    }

    @Override
    public ResponseEntity<?> getTransactionHistories() throws ResourceNotFoundException {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.getReferenceById(userDetails.getId());
            List<Transaction> transactions = transactionRepository.findByUser(user);
            if (transactions.isEmpty()) {
                throw new ResourceNotFoundException("You have no transactions yet!");
            }
            List<ProductTransaction> productTransactionList = new ArrayList<>();
            transactions.forEach(transaction -> {
                List<ProductTransaction> productTransaction = productTransactionRepository.findByTransaction(transaction);
                productTransaction.forEach(pt -> {
                    productTransactionList.add(pt);
                });
            });
            List<TransactionHistoriesResponseDTO> responseDTO = new ArrayList<>();
            productTransactionList.forEach(ptl -> {
                TransactionHistoriesResponseDTO dto = TransactionHistoriesResponseDTO.builder()
                    .productName(ptl.getProduct().getName())
                    .productPrice(ptl.getProduct().getPrice())
                    .totalItems(ptl.getItemUnit())
                    .totalPricePerItem(ptl.getTotalPrice())
                    .build();
                responseDTO.add(dto);
            });
            Map<String, Object> metaData = new HashMap<>();
            metaData.put("_total", responseDTO.size());
            return ResponseHandler.generateSuccessResponseWithMeta(HttpStatus.OK, ZonedDateTime.now(), "Successfully reterieve data!", responseDTO, metaData);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateErrorResponse(HttpStatus.NOT_FOUND, ZonedDateTime.now(), e.getMessage(), EErrorCode.MISSING_PARAM.getCode());
        }
    }

    @Override
    public ResponseEntity<?> getTransactionHistory(Long id) throws ResourceNotFoundException {
        try {
            ProductTransaction pt = productTransactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Error: Transaction is not found!"));
            TransactionHistoryResponseDTO responseDTO = TransactionHistoryResponseDTO.builder()
                .id(pt.getId())
                .totalItemUnit(pt.getItemUnit())
                .totalPrice(pt.getTotalPrice())
                .shippingPrice(null)
                .sendAddress(pt.getTransaction().getSendAddress())
                .paymentMethod(pt.getTransaction().getPayment().getName().name())
                .status(pt.getTransaction().getTransactionStatus().getName().name())
                .receipt(pt.getTransaction().getReceipt())
                .build();
    
            return ResponseHandler.generateSuccessResponse(HttpStatus.OK, ZonedDateTime.now(), "Successfully retrieved data!", responseDTO);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateErrorResponse(HttpStatus.NOT_FOUND, ZonedDateTime.now(), e.getMessage(), EErrorCode.MISSING_PARAM.getCode());
        }
    }
}
