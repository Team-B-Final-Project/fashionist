package com.anbit.fashionist.domain.dao;

import com.anbit.fashionist.domain.common.Audit;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "store",schema="public")
public class Store extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address_id", nullable = false)
    private Address address;

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", user=" + user +
                ", name='" + name + '\'' +
                ", address_id=" + address +
                '}';
    }
}
