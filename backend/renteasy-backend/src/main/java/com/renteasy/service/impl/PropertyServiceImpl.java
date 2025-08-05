package com.renteasy.service.impl;

import com.renteasy.dto.PropertyDTO;
import com.renteasy.entity.PropertyEntity;
import com.renteasy.entity.UserEntity;
import com.renteasy.repository.PropertyRepository;
import com.renteasy.repository.UserRepository;
import com.renteasy.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public PropertyDTO createProperty(PropertyDTO propertyDTO) {
        UserEntity user = userRepository.findById(propertyDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!"SELLER".equalsIgnoreCase(user.getRole())) {
            throw new RuntimeException("Only sellers can post properties");
        }

        PropertyEntity property = new PropertyEntity();
        property.setUser(user);
        property.setTitle(propertyDTO.getTitle());
        property.setDescription(propertyDTO.getDescription());
        property.setPlace(propertyDTO.getPlace());
        property.setArea(propertyDTO.getArea());
        property.setBedrooms(propertyDTO.getBedrooms());
        property.setBathrooms(propertyDTO.getBathrooms());
        property.setHospitalsNearby(propertyDTO.getHospitalsNearby() != null ? propertyDTO.getHospitalsNearby() : "NO");
        property.setCollegesNearby(propertyDTO.getCollegesNearby() != null ? propertyDTO.getCollegesNearby() : "NO");
        property.setRentPrice(propertyDTO.getRentPrice());

        PropertyEntity savedProperty = propertyRepository.save(property);
        propertyDTO.setPropertyId(savedProperty.getPropertyId());
        return propertyDTO;
    }

    // LEGACY (returns all, not paged)
    @Override
    public List<PropertyDTO> getPropertiesBySeller(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return propertyRepository.findByUser(user).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // PAGED version for seller
    @Override
    public Page<PropertyDTO> getPropertiesBySellerPaged(Long userId, Pageable pageable) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return propertyRepository.findByUser(user, pageable)
                .map(this::convertToDTO);
    }

    // Paginated and filtered for buyers
    @Override
    public Page<PropertyDTO> getAllProperties(
        String place, Double minArea, Double maxArea, Integer bedrooms, Integer bathrooms,
        Double minPrice, Double maxPrice, String hospitalsNearby, String collegesNearby, Pageable pageable
    ) {
        return propertyRepository.findPropertiesWithFilters(
            place, minArea, maxArea, bedrooms, bathrooms,
            minPrice, maxPrice, hospitalsNearby, collegesNearby, pageable
        ).map(this::convertToDTO);
    }

    @Override
    public PropertyDTO updateProperty(Long propertyId, PropertyDTO propertyDTO) {
        PropertyEntity property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        if (!property.getUser().getUserId().equals(propertyDTO.getUserId())) {
            throw new RuntimeException("You can only update your own properties");
        }

        property.setTitle(propertyDTO.getTitle());
        property.setDescription(propertyDTO.getDescription());
        property.setPlace(propertyDTO.getPlace());
        property.setArea(propertyDTO.getArea());
        property.setBedrooms(propertyDTO.getBedrooms());
        property.setBathrooms(propertyDTO.getBathrooms());
        property.setHospitalsNearby(propertyDTO.getHospitalsNearby() != null ? propertyDTO.getHospitalsNearby() : "NO");
        property.setCollegesNearby(propertyDTO.getCollegesNearby() != null ? propertyDTO.getCollegesNearby() : "NO");
        property.setRentPrice(propertyDTO.getRentPrice());

        PropertyEntity updatedProperty = propertyRepository.save(property);
        return convertToDTO(updatedProperty);
    }

    @Override
    public void deleteProperty(Long propertyId, Long userId) {
        PropertyEntity property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));

        if (!property.getUser().getUserId().equals(userId)) {
            throw new RuntimeException("You can only delete your own properties");
        }

        propertyRepository.delete(property);
    }

    @Override
    public PropertyDTO getPropertyById(Long propertyId) {
        PropertyEntity property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));
        return convertToDTO(property);
    }

    private PropertyDTO convertToDTO(PropertyEntity property) {
        PropertyDTO dto = new PropertyDTO();
        dto.setPropertyId(property.getPropertyId());
        dto.setUserId(property.getUser().getUserId());
        dto.setTitle(property.getTitle());
        dto.setDescription(property.getDescription());
        dto.setPlace(property.getPlace());
        dto.setArea(property.getArea());
        dto.setBedrooms(property.getBedrooms());
        dto.setBathrooms(property.getBathrooms());
        dto.setHospitalsNearby(property.getHospitalsNearby());
        dto.setCollegesNearby(property.getCollegesNearby());
        dto.setRentPrice(property.getRentPrice());
        return dto;
    }
}
