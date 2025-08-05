// File: src/main/java/com/renteasy/dto/InterestDTO.java
package com.renteasy.dto;

import jakarta.validation.constraints.NotNull;
import java.util.Objects;

public class InterestDTO {

    private Long interestId;

    @NotNull(message = "Property ID is required")
    private Long propertyId;

    @NotNull(message = "Buyer ID is required")
    private Long buyerId;

    // Getters and Setters
    public Long getInterestId() {
        return interestId;
    }

    public void setInterestId(Long interestId) {
        this.interestId = interestId;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InterestDTO that = (InterestDTO) o;
        return Objects.equals(interestId, that.interestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(interestId);
    }
}