package com.anbit.fashionist.domain.common;

import com.anbit.fashionist.domain.dao.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDetailsImpl implements UserDetails {
    private static final Long serialVersionUID = 1L;


    private Long userId;
    private Long profilePictureId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    @JsonIgnore
    private String password;

    public UserDetailsImpl(Long id, Long id1, String firstName, String lastName, String email, Character phoneNumber, String password, LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String updatedBy, List<GrantedAuthority> authorities) {
    }

    public Collection<? extends GrantedAuthority> authorities;

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return new UserDetailsImpl(user.getId(), user.getProfile_picture().getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhoneNumber(), user.getPassword(), user.getCreatedAt(), user.getUpdatedAt(), user.getCreatedBy(), user.getUpdatedBy(), authorities);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public String getUsername() {
        return null;
    }


    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return userId.equals(user.userId);
    }
}
