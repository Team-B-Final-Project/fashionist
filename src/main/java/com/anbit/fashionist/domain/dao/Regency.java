package com.anbit.fashionist.domain.dao;

import javax.persistence.*;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "regency", schema = "public")
public class Regency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = Province.class)
    @JoinColumn(name = "province_id")
    private Province province;

    private String name;
}
