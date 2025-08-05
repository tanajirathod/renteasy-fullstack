package com.renteasy.service.impl;

import com.renteasy.dto.LikeDTO;
import com.renteasy.entity.LikeEntity;
import com.renteasy.entity.PropertyEntity;
import com.renteasy.entity.UserEntity;
import com.renteasy.repository.LikeRepository;
import com.renteasy.repository.PropertyRepository;
import com.renteasy.repository.UserRepository;
import com.renteasy.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public LikeDTO likeProperty(LikeDTO likeDTO) {
        PropertyEntity property = propertyRepository.findById(likeDTO.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Property not found"));

        UserEntity user = userRepository.findById(likeDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (likeRepository.existsByPropertyAndUser(property, user)) {
            throw new RuntimeException("You have already liked this property");
        }

        LikeEntity like = new LikeEntity();
        like.setProperty(property);
        like.setUser(user);

        LikeEntity savedLike = likeRepository.save(like);
        likeDTO.setLikeId(savedLike.getLikeId());
        return likeDTO;
    }

    @Override
    public long getLikeCount(Long propertyId) {
        PropertyEntity property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));
        return likeRepository.countByProperty(property);
    }
}