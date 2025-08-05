// File: src/main/java/com/renteasy/dto/LikeDTO.java
package com.renteasy.dto;

import jakarta.validation.constraints.NotNull;
import java.util.Objects;

public class LikeDTO {

    private Long likeId;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Property ID is required")
    private Long propertyId;

    // Getters and Setters
    public Long getLikeId() {
        return likeId;
    }

    public void setLikeId(Long likeId) {
        this.likeId = likeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LikeDTO likeDTO = (LikeDTO) o;
        return Objects.equals(likeId, likeDTO.likeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(likeId);
    }
}