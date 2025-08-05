package com.renteasy.controller;

import com.renteasy.dto.LikeDTO;
import com.renteasy.service.LikeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    // Like a property
    @PostMapping
    public LikeDTO likeProperty(@RequestBody @Valid LikeDTO likeDTO) {
        return likeService.likeProperty(likeDTO);
    }

    // Get like count for a property
    @GetMapping("/{propertyId}/count")
    public long getLikeCount(@PathVariable Long propertyId) {
        return likeService.getLikeCount(propertyId);
    }
}


