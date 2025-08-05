package com.renteasy.service;

import com.renteasy.dto.PropertyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface PropertyService {
    PropertyDTO createProperty(PropertyDTO propertyDTO);
    List<PropertyDTO> getPropertiesBySeller(Long userId); // legacy
    Page<PropertyDTO> getPropertiesBySellerPaged(Long userId, Pageable pageable); // NEW
    PropertyDTO updateProperty(Long propertyId, PropertyDTO propertyDTO);
    void deleteProperty(Long propertyId, Long userId);
    Page<PropertyDTO> getAllProperties(
        String place, Double minArea, Double maxArea, Integer bedrooms, Integer bathrooms,
        Double minPrice, Double maxPrice, String hospitalsNearby, String collegesNearby, Pageable pageable
    );
    PropertyDTO getPropertyById(Long propertyId);
}
