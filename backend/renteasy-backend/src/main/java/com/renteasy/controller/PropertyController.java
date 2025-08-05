package com.renteasy.controller;

import com.renteasy.dto.PropertyDTO;
import com.renteasy.service.PropertyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    // BUYER: List all properties, with pagination & filters
    @GetMapping
    public Page<PropertyDTO> getAllProperties(
            @RequestParam(required = false) String place,
            @RequestParam(required = false) Double minArea,
            @RequestParam(required = false) Double maxArea,
            @RequestParam(required = false) Integer bedrooms,
            @RequestParam(required = false) Integer bathrooms,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String hospitalsNearby,
            @RequestParam(required = false) String collegesNearby,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return propertyService.getAllProperties(
                place, minArea, maxArea, bedrooms, bathrooms, minPrice, maxPrice,
                hospitalsNearby, collegesNearby, pageable
        );
    }

    // SELLER: List my properties, paged
    @GetMapping("/seller/{sellerId}")
    public Page<PropertyDTO> getPropertiesBySellerPaged(
            @PathVariable Long sellerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return propertyService.getPropertiesBySellerPaged(sellerId, pageable);
    }

    // SELLER: List all by seller (legacy, returns full list)
    @GetMapping("/seller/{sellerId}/all")
    public List<PropertyDTO> getPropertiesBySeller(@PathVariable Long sellerId) {
        return propertyService.getPropertiesBySeller(sellerId);
    }

    // CRUD endpoints...
    @PostMapping("/add/{userId}")
    public ResponseEntity<PropertyDTO> addProperty(
            @PathVariable Long userId,
            @RequestBody @Valid PropertyDTO propertyDTO
    ) {
        propertyDTO.setUserId(userId);
        return ResponseEntity.ok(propertyService.createProperty(propertyDTO));
    }

    @PutMapping("/{propertyId}")
    public ResponseEntity<PropertyDTO> updateProperty(
            @PathVariable Long propertyId,
            @RequestBody @Valid PropertyDTO propertyDTO
    ) {
        return ResponseEntity.ok(propertyService.updateProperty(propertyId, propertyDTO));
    }

    @DeleteMapping("/{propertyId}")
    public ResponseEntity<Void> deleteProperty(
            @PathVariable Long propertyId,
            @RequestParam Long userId
    ) {
        propertyService.deleteProperty(propertyId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public PropertyDTO getPropertyById(@PathVariable Long id) {
        return propertyService.getPropertyById(id);
    }
}



