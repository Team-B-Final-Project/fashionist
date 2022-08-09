package com.anbit.fashionist.domain.dao;

import com.anbit.fashionist.domain.common.Audit;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "product",schema="public")
public class Address extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

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
        return "Address [city=" + city + 
            ", fullAddress=" + fullAddress + 
            ", id=" + id + 
            ", name=" + name + 
            ", phoneNumber=" + phoneNumber + 
            ", province=" + province + 
            ", user=" + user + "]";
    }

}



