package com.anbit.fashionist.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anbit.fashionist.domain.dao.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean exixstsByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
