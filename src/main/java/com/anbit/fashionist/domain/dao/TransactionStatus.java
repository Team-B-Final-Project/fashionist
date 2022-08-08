package com.anbit.fashionist.domain.dao;

import javax.persistence.*;

import com.anbit.fashionist.constant.ETransactionStatus;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "transaction_status")
public class TransactionStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private ETransactionStatus name;
}
