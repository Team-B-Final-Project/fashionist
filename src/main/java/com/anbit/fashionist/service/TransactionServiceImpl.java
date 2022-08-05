package com.anbit.fashionist.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.anbit.fashionist.constant.EErrorCode;
import com.anbit.fashionist.constant.EPayment;
import com.anbit.fashionist.constant.ETransactionStatus;
import com.anbit.fashionist.domain.dao.Cart;
import com.anbit.fashionist.domain.dao.Payment;
import com.anbit.fashionist.domain.dao.ProductTransaction;
import com.anbit.fashionist.domain.dao.Transaction;
import com.anbit.fashionist.domain.dto.CreateTransactionRequestDTO;
import com.anbit.fashionist.handler.ResponseHandler;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.repository.AddressRepository;
import com.anbit.fashionist.repository.CartRepository;
import com.anbit.fashionist.repository.PaymentRepository;
import com.anbit.fashionist.repository.ProductTransactionRepository;
import com.anbit.fashionist.repository.TransactionRepository;
import com.anbit.fashionist.repository.TransactionStatusRepository;
import com.anbit.fashionist.repository.UserRepository;
import com.anbit.fashionist.util.RandomString;

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

    private RandomString randomString;
    
    @Override
    public ResponseEntity<?> createTransaction(CreateTransactionRequestDTO requestDTO) throws ResourceNotFoundException {
        try {
            List<Cart> cartList = new ArrayList<>();
            Transaction transaction = Transaction.builder()
                .id(randomString.generate("TSN"))
                .user(userRepository.getReferenceById(requestDTO.getUserId()))
                .sendAddress(addressRepository.getReferenceById(requestDTO.getSendAddressId()))
                .transactionStatus(transactionStatusRepository.findByName(ETransactionStatus.WAITING_PAYMENT).get())
                .build();

            switch (requestDTO.getPaymentMethod()) {
                case "BANK_BCA":
                    Payment bca = paymentRepository.findByName(EPayment.BANK_BCA).orElseThrow(() -> new ResourceNotFoundException("Error: Payment is not found!"));
                    transaction.setPayment(bca);
                    break;
                case "BANK_BNI":
                    Payment bni = paymentRepository.findByName(EPayment.BANK_BNI).orElseThrow(() -> new ResourceNotFoundException("Error: Payment is not found!"));
                    transaction.setPayment(bni);
                    break;
                case "BANK_BRI":
                    Payment bri = paymentRepository.findByName(EPayment.BANK_BRI).orElseThrow(() -> new ResourceNotFoundException("Error: Payment is not found!"));
                    transaction.setPayment(bri);
                    break;
                case "BANK_MANDIRI":
                    Payment mandiri = paymentRepository.findByName(EPayment.BANK_MANDIRI).orElseThrow(() -> new ResourceNotFoundException("Error: Payment is not found!"));
                    transaction.setPayment(mandiri);
                    break;
                case "BANK_PERMATA":
                    Payment permata = paymentRepository.findByName(EPayment.BANK_PERMATA).orElseThrow(() -> new ResourceNotFoundException("Error: Payment is not found!"));
                    transaction.setPayment(permata);
                    break;
            }
            Transaction newTransaction = transactionRepository.save(transaction);

            for (Long cartId : requestDTO.getCartShipping().keySet()){
                Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart with ID" + cartId + "doesn't exist"));
                cartList.add(cart);
                ProductTransaction productTransaction = ProductTransaction.builder()
                    .product(cart.getProduct())
                    .transaction(newTransaction)
                    .itemUnit(cart.getItemUnit())
                    .totalPrice(cart.getTotalPrice())
                    .shipping(requestDTO.getCartShipping().get(cartId))
                    .build();
                productTransactionRepository.save(productTransaction);
            }

            return ResponseHandler.generateSuccessResponse(HttpStatus.OK, ZonedDateTime.now(), "New transaction is created successfully!", null);
        } catch (ResourceNotFoundException e) {
            return ResponseHandler.generateErrorResponse(HttpStatus.NOT_FOUND, ZonedDateTime.now(), e.getMessage(), EErrorCode.MISSING_PARAM.getCode());
        }
    }
}
