package com.anbit.fashionist.domain.dao;

import lombok.*;


import javax.persistence.*;

import com.anbit.fashionist.constant.ERole;


@Getter
@Setter
@Entity
@Table(name = "role")
@NoArgsConstructor
public class Role{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private ERole name;


}
