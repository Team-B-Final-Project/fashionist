package com.anbit.fashionist.domain.dao;

import com.anbit.fashionist.domain.common.Audit;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "product",schema="public")
public class Address extends Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "province", nullable = false)
    private String province;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "full_address", nullable = false)
    private String fullAddress;

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", fullAddress='" + fullAddress + '\'' +
                '}';
    }
}
