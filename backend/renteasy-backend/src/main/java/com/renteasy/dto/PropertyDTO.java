package com.renteasy.dto;

import jakarta.validation.constraints.*;
import java.util.Objects;

public class PropertyDTO {

    private Long propertyId;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
    private String title;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotBlank(message = "Place is required")
    @Size(min = 3, max = 100, message = "Place must be between 3 and 100 characters")
    private String place;

    @NotNull(message = "Area is required")
    @Positive(message = "Area must be positive")
    private Double area;

    @NotNull(message = "Number of bedrooms is required")
    @PositiveOrZero(message = "Number of bedrooms cannot be negative")
    private Integer bedrooms;

    @NotNull(message = "Number of bathrooms is required")
    @PositiveOrZero(message = "Number of bathrooms cannot be negative")
    private Integer bathrooms;

    private String hospitalsNearby;

    private String collegesNearby;

    @NotNull(message = "Rent price is required")
    @Positive(message = "Rent price must be positive")
    private Double rentPrice;

    // Getters and Setters
    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Integer getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
    }

    public Integer getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(Integer bathrooms) {
        this.bathrooms = bathrooms;
    }

    public String getHospitalsNearby() {
        return hospitalsNearby;
    }

    public void setHospitalsNearby(String hospitalsNearby) {
        this.hospitalsNearby = hospitalsNearby;
    }

    public String getCollegesNearby() {
        return collegesNearby;
    }

    public void setCollegesNearby(String collegesNearby) {
        this.collegesNearby = collegesNearby;
    }

    public Double getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(Double rentPrice) {
        this.rentPrice = rentPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyDTO that = (PropertyDTO) o;
        return Objects.equals(propertyId, that.propertyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(propertyId);
    }
}