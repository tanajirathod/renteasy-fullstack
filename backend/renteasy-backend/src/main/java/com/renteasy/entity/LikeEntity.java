package com.renteasy.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "LIKES")
public class LikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "like_seq")
    @SequenceGenerator(name = "like_seq", sequenceName = "LIKE_SEQ", allocationSize = 1)
    @Column(name = "LIKE_ID")
    private Long likeId;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "PROPERTY_ID", nullable = false)
    private PropertyEntity property;

    @Column(name = "LIKED_AT", nullable = false)
    private LocalDateTime likedAt = LocalDateTime.now();

    // Getters and Setters
    public Long getLikeId() {
        return likeId;
    }

    public void setLikeId(Long likeId) {
        this.likeId = likeId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public PropertyEntity getProperty() {
        return property;
    }

    public void setProperty(PropertyEntity property) {
        this.property = property;
    }

    public LocalDateTime getLikedAt() {
        return likedAt;
    }

    public void setLikedAt(LocalDateTime likedAt) {
        this.likedAt = likedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LikeEntity that = (LikeEntity) o;
        return Objects.equals(likeId, that.likeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(likeId);
    }
}