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
@Table(name = "address" ,schema="public")
public class Address extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Village.class)
    @JoinColumn(name = "village_id")
    private Village village;

    @Column(name = "full_address", nullable = false)
    private String fullAddress;

    @Override
    public String toString() {
        return "\n Address [fullAddress=" + fullAddress + 
            ", id=" + id + 
            ", name=" + name + 
            ", phoneNumber=" + phoneNumber + 
            ", user=" + user + 
            ", village=" + village + "]";
    }
}



