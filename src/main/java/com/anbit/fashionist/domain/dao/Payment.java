package com.anbit.fashionist.domain.dao;

import javax.persistence.*;

import com.anbit.fashionist.constant.EPayment;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "payment", schema = "public")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private EPayment name;
}
