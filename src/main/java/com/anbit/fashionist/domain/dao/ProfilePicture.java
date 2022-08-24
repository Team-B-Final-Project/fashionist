package com.anbit.fashionist.domain.dao;

import com.anbit.fashionist.domain.common.Audit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "profile_picture", schema = "public")
public class ProfilePicture extends Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    private Long size;

    private byte[] data;
}
