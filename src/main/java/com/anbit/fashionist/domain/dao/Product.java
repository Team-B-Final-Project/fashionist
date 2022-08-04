package com.anbit.fashionist.domain.dao;

import com.anbit.fashionist.domain.dto.ProductResponseDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product extends Audit{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store storeId;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "description")
    private String description;

    @Column(nullable = false, name = "price")
    private Float price;

    @Column(nullable = false, name = "stock")
    private Integer stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category categoryId;

    public ProductResponseDTO convertToResponseDTO() {
        return ProductResponseDTO.builder()
                .productId(this.id)
                .name(this.name)
                .description(this.description)
                .price(this.price)
                .stock(this.stock)
                .build();
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}
