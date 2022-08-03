package com.anbit.fashionist.domain.dao;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.anbit.fashionist.domain.common.Audit;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user", schema = "public", uniqueConstraints = {@UniqueConstraint(columnNames = "username"), @UniqueConstraint(columnNames = "emailAddress")})
public class User extends Audit{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profile_picture_id")
    private ProfilePicture profile_picture;

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private Character phoneNumber;

    private String password;

    private Set<Role> roles = new HashSet<>();
}

















