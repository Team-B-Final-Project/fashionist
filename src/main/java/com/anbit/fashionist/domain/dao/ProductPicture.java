package com.anbit.fashionist.domain.dao;

import javax.persistence.*;

import org.hibernate.annotations.Type;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "product_picture", schema = "public")
public class ProductPicture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Product.class)
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer level;

    private String name;

    private String type;

    private Long size;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    private String url;
}
