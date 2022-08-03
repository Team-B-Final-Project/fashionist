package com.anbit.fashionist.repository;

import com.anbit.fashionist.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmailAddress(String email);

    boolean existsByUsername(String username);

    boolean existsByEmailAddress(String email);

}