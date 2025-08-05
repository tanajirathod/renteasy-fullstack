package com.renteasy.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.renteasy.entity.InterestEntity;
import com.renteasy.entity.PropertyEntity;
import com.renteasy.entity.UserEntity;

public interface InterestRepository extends JpaRepository<InterestEntity, Long> {
    @Query("SELECT i FROM InterestEntity i WHERE i.property.propertyId = :propertyId")
    List<InterestEntity> findByPropertyId(@Param("propertyId") Long propertyId);
    boolean existsByPropertyAndBuyer(PropertyEntity property, UserEntity buyer);
    List<InterestEntity> findByBuyer(UserEntity buyer);

}