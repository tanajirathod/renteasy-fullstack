package com.renteasy.service;

import com.renteasy.dto.LikeDTO;

public interface LikeService {
    LikeDTO likeProperty(LikeDTO likeDTO);
    long getLikeCount(Long propertyId);
}