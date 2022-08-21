package com.anbit.fashionist.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import com.anbit.fashionist.controller.TransactionController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.anbit.fashionist.constant.EPayment;
import com.anbit.fashionist.constant.EShipping;
import com.anbit.fashionist.constant.ETransactionStatus;
import com.anbit.fashionist.domain.common.UserDetailsImpl;
import com.anbit.fashionist.domain.dao.Address;
import com.anbit.fashionist.domain.dao.Cart;
import com.anbit.fashionist.domain.dao.Payment;
import com.anbit.fashionist.domain.dao.Product;
import com.anbit.fashionist.domain.dao.ProductTransaction;
import com.anbit.fashionist.domain.dao.Shipping;
import com.anbit.fashionist.domain.dao.Transaction;
import com.anbit.fashionist.domain.dao.TransactionStatus;
import com.anbit.fashionist.domain.dao.User;
import com.anbit.fashionist.domain.dto.CreateTransactionRequestDTO;
import com.anbit.fashionist.domain.dto.CreateTransactionResponseDTO;
import com.anbit.fashionist.domain.dto.TransactionHistoriesResponseDTO;
import com.anbit.fashionist.domain.dto.TransactionHistoryResponseDTO;
import com.anbit.fashionist.handler.ResponseHandler;
import com.anbit.fashionist.helper.ResourceNotFoundException;
import com.anbit.fashionist.repository.AddressRepository;
import com.anbit.fashionist.repository.CartRepository;
import com.anbit.fashionist.repository.PaymentRepository;
import com.anbit.fashionist.repository.ProductRepository;
import com.anbit.fashionist.repository.ProductTransactionRepository;
import com.anbit.fashionist.repository.ShippingRepository;
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
    ProductRepository productRepository;

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

    @Autowired
    RandomString randomString;

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
    private static final String loggerLine = "---------------------------------------";

    
    @Override
    public ResponseEntity<?> createTransaction(CreateTransactionRequestDTO requestDTO) throws ResourceNotFoundException {
        List<Cart> cartList = new ArrayList<>();
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.getReferenceById(userDetails.getId());
        Payment payment = paymentRepository.findByName(EPayment.valueOf(requestDTO.getPaymentMethod().toUpperCase())).orElseThrow(() -> new ResourceNotFoundException("Payment not found!"));
        Address address = addressRepository.findById(requestDTO.getSendAddressId()).orElseThrow(() -> new ResourceNotFoundException("Address not found!"));
        if (address.getUser() != user) {
            throw new ResourceNotFoundException("Address not found!");
        }
        List<Float> productPrices = new ArrayList<>();
        List<Integer> itemUnits = new ArrayList<>();
        requestDTO.getCartShipping().keySet().forEach(cartId -> {
            try {
                Cart cartPrice = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart with ID " + cartId + " not found!"));
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
            Shipping shipping = shippingRepository.findByName(EShipping.valueOf(requestDTO.getCartShipping().get(cartId).toUpperCase())).orElseThrow(() -> new ResourceNotFoundException("Shipping not found!"));
            ProductTransaction productTransaction = ProductTransaction.builder()
                .product(cart.getProduct())
                .transaction(newTransaction)
                .itemUnit(cart.getItemUnit())
                .totalPrice(cart.getTotalPrice())
                .shipping(shipping)
                .build();
            productTransactionRepository.save(productTransaction);
            cartRepository.delete(cart);
        }
        String virtualAccount = randomString.generateVirtualAccount(16);
        logger.info(loggerLine);
        logger.info("Create Transaction " + cartList);
        logger.info(loggerLine);
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "New transaction is created successfully!", new CreateTransactionResponseDTO(totalPrice.floatValue(), virtualAccount));
    }

    @Override
    public ResponseEntity<?> getTransactionHistories() throws ResourceNotFoundException {
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
                .transactionId(ptl.getTransaction().getId())
                .productName(ptl.getProduct().getName())
                .productPrice(ptl.getProduct().getPrice())
                .totalItems(ptl.getItemUnit())
                .totalPricePerItem(ptl.getTotalPrice())
                .build();
            responseDTO.add(dto);
        });
        Map<String, Object> metaData = new HashMap<>();
        metaData.put("_total", responseDTO.size());
        logger.info(loggerLine);
        logger.info("Transaction Histories " + responseDTO);
        logger.info(loggerLine);
        return ResponseHandler.generateSuccessResponseWithMeta(HttpStatus.OK, "Successfully reterieve data!", responseDTO, metaData);
    }

    @Override
    @Transactional
    public ResponseEntity<?> getTransactionHistory(Long id) throws ResourceNotFoundException {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.getReferenceById(userDetails.getId());
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Error: Transaction is not found!"));
        if (transaction.getUser() != user) {
            throw new ResourceNotFoundException("Transaction is not found!");
        }
        List<ProductTransaction> productTransactions = productTransactionRepository.findByTransaction(transaction);
        List<Product> products = new ArrayList<>();
        productTransactions.forEach(pr -> {
            products.add(pr.getProduct());
        });

        TransactionHistoryResponseDTO responseDTO = TransactionHistoryResponseDTO.builder()
            .id(transaction.getId())
            .totalItemUnit(transaction.getTotalItemUnit())
            .totalPrice(transaction.getTotalPrice())
            .sendAddress(null)
            .shippingPrice(null)
            .paymentMethod(transaction.getPayment().getName().name())
            .status(transaction.getTransactionStatus().getName().name())
            .receipt(transaction.getReceipt())
            .build();
            responseDTO.setProducts(products);
            responseDTO.setSendAddress(transaction.getSendAddress());
            logger.info(loggerLine);
            logger.info("Transaction History " + responseDTO);
            logger.info(loggerLine);
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "Successfully retrieved data!", responseDTO);
    }

    @Override
    public ResponseEntity<?> makePayment(Long transactionId) throws ResourceNotFoundException {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.getReferenceById(userDetails.getId());
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new ResourceNotFoundException("Transaction not found!"));
        if (transaction.getUser() != user) {
            throw new ResourceNotFoundException("Transaction is not found!");
        }
        TransactionStatus transactionStatus = transactionStatusRepository.findByName(ETransactionStatus.PACKING).orElseThrow(() -> new ResourceNotFoundException("Transaction status not found!"));
        transaction.setTransactionStatus(transactionStatus);
        transactionRepository.save(transaction);
        logger.info(loggerLine);
        logger.info("Make Payment " + transaction);
        logger.info(loggerLine);
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "Payment success!", null);
    }

    @Override
    public ResponseEntity<?> sendProduct(Long transactionId, String receipt) throws ResourceNotFoundException {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.getReferenceById(userDetails.getId());
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new ResourceNotFoundException("Transaction not found!"));
        if (transaction.getUser() != user) {
            throw new ResourceNotFoundException("Transaction is not found!");
        }
        TransactionStatus transactionStatus = transactionStatusRepository.findByName(ETransactionStatus.SENT).orElseThrow(() -> new ResourceNotFoundException("Transaction status not found!"));
        transaction.setTransactionStatus(transactionStatus);
        transaction.setReceipt(receipt);
        transactionRepository.save(transaction);
        logger.info(loggerLine);
        logger.info("Successfully input receipt! " + transaction);
        logger.info(loggerLine);
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "Successfully input receipt!", null);
    }
    
    @Override
    public ResponseEntity<?> productDelivered(Long transactionId, String receipt) throws ResourceNotFoundException {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.getReferenceById(userDetails.getId());
        Transaction transaction = transactionRepository.findById(transactionId).orElseThrow(() -> new ResourceNotFoundException("Transaction not found!"));
        if (transaction.getUser() != user) {
            throw new ResourceNotFoundException("Transaction is not found!");
        }
        logger.info(loggerLine);
        logger.info("Payment success " + transaction);
        logger.info(loggerLine);
        return ResponseHandler.generateSuccessResponse(HttpStatus.OK, "Payment success!", null);
    }
}
