package com.anbit.fashionist.domain.dao;

import javax.persistence.*;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "village", schema = "public")
public class Village {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, targetEntity = District.class)
    @JoinColumn(name = "district_id")
    private District district;

    private String name;

    private String postal;
}
