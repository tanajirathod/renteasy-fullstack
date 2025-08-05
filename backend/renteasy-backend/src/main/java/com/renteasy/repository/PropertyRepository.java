package com.renteasy.repository;

import com.renteasy.entity.PropertyEntity;
import com.renteasy.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PropertyRepository extends JpaRepository<PropertyEntity, Long> {
    // Old: all properties by seller
    List<PropertyEntity> findByUser(UserEntity user);

    // NEW: paged properties by seller
    Page<PropertyEntity> findByUser(UserEntity user, Pageable pageable);

    @Query("SELECT p FROM PropertyEntity p WHERE " +
           "(:place IS NULL OR LOWER(p.place) LIKE LOWER(CONCAT('%', :place, '%'))) AND " +
           "(:minArea IS NULL OR p.area >= :minArea) AND " +
           "(:maxArea IS NULL OR p.area <= :maxArea) AND " +
           "(:bedrooms IS NULL OR p.bedrooms = :bedrooms) AND " +
           "(:bathrooms IS NULL OR p.bathrooms = :bathrooms) AND " +
           "(:minPrice IS NULL OR p.rentPrice >= :minPrice) AND " +
           "(:maxPrice IS NULL OR p.rentPrice <= :maxPrice) AND " +
           "(:hospitalsNearby IS NULL OR p.hospitalsNearby = :hospitalsNearby) AND " +
           "(:collegesNearby IS NULL OR p.collegesNearby = :collegesNearby)"
    )
    Page<PropertyEntity> findPropertiesWithFilters(
        @Param("place") String place,
        @Param("minArea") Double minArea,
        @Param("maxArea") Double maxArea,
        @Param("bedrooms") Integer bedrooms,
        @Param("bathrooms") Integer bathrooms,
        @Param("minPrice") Double minPrice,
        @Param("maxPrice") Double maxPrice,
        @Param("hospitalsNearby") String hospitalsNearby,
        @Param("collegesNearby") String collegesNearby,
        Pageable pageable
    );
}
