package com.epam.cloudgantt.repository;

import com.epam.cloudgantt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByEmail(String email);
    Optional<User> findByVerificationCode(String code);

    Optional<User> findByEmail(String email);
}
