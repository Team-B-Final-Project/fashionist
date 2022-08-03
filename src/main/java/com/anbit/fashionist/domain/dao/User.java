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
@Builder
@Entity
@Table(name = "user", schema = "public", uniqueConstraints = {@UniqueConstraint(columnNames = "username"), @UniqueConstraint(columnNames = "email"), @UniqueConstraint(columnNames = "phoneNumber")})
public class User extends Audit{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profile_picture_id")
    private ProfilePicture profilePicture;

    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private Character phoneNumber;

    private String password;

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
}

















