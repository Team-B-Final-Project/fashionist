package com.anbit.fashionist.domain.dao;

import javax.persistence.*;

import com.anbit.fashionist.domain.common.Audit;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "product_transaction")
public class ProductTransaction extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Product.class)
    @JoinColumn(name = "product_id")
    private Product product;
    
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Transaction.class)
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    private Integer itemUnit;

    private Float totalPrice;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Shipping.class)
    @JoinColumn(name = "shipping_id")
    private Shipping shipping;
}
