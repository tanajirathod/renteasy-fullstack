package com.renteasy.controller;

import com.renteasy.dto.InterestDTO;
import com.renteasy.dto.PropertyDTO;
import com.renteasy.dto.UserDTO;
import com.renteasy.service.InterestService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interests")
public class InterestController {

    @Autowired
    private InterestService interestService;

    // Buyer expresses interest
    @PostMapping
    public InterestDTO expressInterest(@RequestBody @Valid InterestDTO interestDTO) {
        return interestService.expressInterest(interestDTO);
    }

    // Get all buyers interested in a property (seller)
    @GetMapping("/property/{propertyId}/buyers")
    public List<UserDTO> getInterestedBuyers(@PathVariable Long propertyId) {
        return interestService.getInterestedBuyers(propertyId);
    }

    // Get all properties a buyer is interested in
    @GetMapping("/buyer/{buyerEmail}")
    public List<PropertyDTO> getInterestsByBuyer(@PathVariable String buyerEmail) {
        return interestService.getPropertiesInterestedByBuyer(buyerEmail);
    }
}
