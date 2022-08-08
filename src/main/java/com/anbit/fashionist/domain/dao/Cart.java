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
@Table(name = "cart", schema = "public")
public class Cart extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Product.class)
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer itemUnit;

    private Float totalPrice;
}
