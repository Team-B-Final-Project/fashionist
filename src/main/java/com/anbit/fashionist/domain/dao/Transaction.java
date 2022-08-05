package com.anbit.fashionist.domain.dao;

import java.util.List;

import javax.persistence.*;

import com.anbit.fashionist.domain.common.Audit;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "transaction", schema = "public")
public class Transaction extends Audit {
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    private Float totalPrice;

    private Integer totalItemUnit;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Address.class)
    @JoinColumn(name = "address_id")
    private Address sendAddress;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Payment.class)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = TransactionStatus.class)
    @JoinColumn(name = "transaction_status_id")
    private TransactionStatus transactionStatus;

    private String receipt;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "transaction")
    private List<Product> products;
}
