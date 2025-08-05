// File: src/main/java/com/renteasy/entity/InterestEntity.java
package com.renteasy.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "INTERESTS")
public class InterestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "interest_seq")
    @SequenceGenerator(name = "interest_seq", sequenceName = "INTEREST_SEQ", allocationSize = 1)
    @Column(name = "INTEREST_ID")
    private Long interestId;

    @ManyToOne
    @JoinColumn(name = "PROPERTY_ID", nullable = false)
    private PropertyEntity property;

    @ManyToOne
    @JoinColumn(name = "BUYER_ID", nullable = false)
    private UserEntity buyer;

    @Column(name = "INTEREST_DATE", nullable = false)
    private LocalDateTime interestDate = LocalDateTime.now();

    // Getters and Setters
    public Long getInterestId() {
        return interestId;
    }

    public void setInterestId(Long interestId) {
        this.interestId = interestId;
    }

    public PropertyEntity getProperty() {
        return property;
    }

    public void setProperty(PropertyEntity property) {
        this.property = property;
    }

    public UserEntity getBuyer() {
        return buyer;
    }

    public void setBuyer(UserEntity buyer) {
        this.buyer = buyer;
    }

    public LocalDateTime getInterestDate() {
        return interestDate;
    }

    public void setInterestDate(LocalDateTime interestDate) {
        this.interestDate = interestDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InterestEntity that = (InterestEntity) o;
        return Objects.equals(interestId, that.interestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(interestId);
    }
}