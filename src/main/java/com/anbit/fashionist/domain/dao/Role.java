package com.anbit.fashionist.domain.dao;

import lombok.*;

import javax.management.ObjectName;
import javax.persistence.*;

import com.anbit.fashionist.constant.ERole;

import java.util.List;


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
