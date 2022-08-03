package com.anbit.fashionist.domain.dao;

import lombok.*;

import javax.persistence.*;

import com.anbit.fashionist.constant.ERole;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private ERole name;
}
