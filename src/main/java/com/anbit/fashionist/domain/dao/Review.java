package com.anbit.fashionist.domain.dao;

import com.anbit.fashionist.domain.common.Audit;


import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "review", schema = "public")
public class Review extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double rating;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Transaction.class)
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

}
