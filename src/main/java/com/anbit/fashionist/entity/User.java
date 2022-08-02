package com.anbit.fashionist.entity;

import com.anbit.fashionist.domain.dto.UserResponseDTO;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user", schema = "public", uniqueConstraints = {@UniqueConstraint(columnNames = "username"), @UniqueConstraint(columnNames = "emailAddress")})
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer userId;

    private String firstName;

    private String lastName;

    private String username;

    private String emailAddress;

    private String password;

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public User(String firstName, String lastName, String username, String emailAddress, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.emailAddress = emailAddress;
        this.password = password;
    }


    public UserResponseDTO convertToResponse(){
        return UserResponseDTO.builder()
                .userId(this.userId)
                .firstName(this.firstName)
                .lastName(this.lastName)
                .username(this.username)
                .emailAddress(this.emailAddress)
                .roles(this.roles.stream().map(role -> role.getName().toString()).collect(Collectors.toList()))
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
}
