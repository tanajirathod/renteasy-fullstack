package com.renteasy.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "PROPERTIES")
public class PropertyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "property_seq")
    @SequenceGenerator(name = "property_seq", sequenceName = "PROPERTY_SEQ", allocationSize = 1)
    @Column(name = "PROPERTY_ID")
    private Long propertyId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserEntity user;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PLACE", nullable = false)
    private String place;

    @Column(name = "AREA", nullable = false)
    private Double area;

    @Column(name = "BEDROOMS", nullable = false)
    private Integer bedrooms;

    @Column(name = "BATHROOMS", nullable = false)
    private Integer bathrooms;

    @Column(name = "HOSPITALS_NEARBY")
    private String hospitalsNearby;

    @Column(name = "COLLEGES_NEARBY")
    private String collegesNearby;

    @Column(name = "RENT_PRICE", nullable = false)
    private Double rentPrice;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters and Setters
    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertyEntity that = (PropertyEntity) o;
        return Objects.equals(propertyId, that.propertyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(propertyId);
    }
}