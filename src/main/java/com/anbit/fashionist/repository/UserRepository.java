package com.anbit.fashionist.repository;


import com.anbit.fashionist.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmailAddress(String emailAddress);
    Boolean existsByUsername(String username);
    Boolean existsByEmailAddress(String emailAddress);
}
