package com.anbit.fashionist.domain.dao;

import javax.persistence.*;

import com.anbit.fashionist.constant.ECategory;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "category", schema = "public")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ECategory name;

    private String slug;
}
