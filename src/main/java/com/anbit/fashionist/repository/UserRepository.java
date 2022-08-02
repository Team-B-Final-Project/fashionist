package com.anbit.fashionist.repository;

import java.util.Optional;

import com.anbit.fashionist.domain.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmailAddress(String emailAddress);
    Boolean existsByUsername(String username);
    Boolean existsByEmailAddress(String emailAddress);
}
