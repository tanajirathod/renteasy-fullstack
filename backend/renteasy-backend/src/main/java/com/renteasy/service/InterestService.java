package com.renteasy.service;

import com.renteasy.dto.InterestDTO;
import com.renteasy.dto.PropertyDTO;
import com.renteasy.dto.UserDTO;

import java.util.List;

public interface InterestService {
    InterestDTO expressInterest(InterestDTO interestDTO);
    List<UserDTO> getInterestedBuyers(Long propertyId);
    void removeInterestById(Long interestId);
    List<PropertyDTO> getPropertiesInterestedByBuyer(String buyerEmail);
}