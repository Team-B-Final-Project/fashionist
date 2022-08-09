package com.anbit.fashionist.domain.dao;

import lombok.*;

import javax.persistence.*;

import com.anbit.fashionist.constant.ERole;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private ERole name;
}
