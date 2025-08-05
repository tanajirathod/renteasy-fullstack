// File: src/main/java/com/renteasy/repository/LikeRepository.java
package com.renteasy.repository;

import com.renteasy.entity.LikeEntity;
import com.renteasy.entity.PropertyEntity;
import com.renteasy.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    List<LikeEntity> findByProperty(PropertyEntity property);
    boolean existsByPropertyAndUser(PropertyEntity property, UserEntity user);
    long countByProperty(PropertyEntity property);
}
