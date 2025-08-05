// File: src/main/java/com/renteasy/repository/UserRepository.java
package com.renteasy.repository;

import com.renteasy.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
Optional<UserEntity> findByEmailIgnoreCase(String email);

}