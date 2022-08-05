package com.anbit.fashionist.domain.dao;

import javax.persistence.*;

import com.anbit.fashionist.constant.EShipping;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "shipping", schema = "public")
public class Shipping {
    private Integer id;

    @Enumerated(EnumType.STRING)
    private EShipping name;
}
