package com.renteasy.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.renteasy.dto.InterestDTO;
import com.renteasy.dto.PropertyDTO;
import com.renteasy.dto.UserDTO;
import com.renteasy.entity.InterestEntity;
import com.renteasy.entity.PropertyEntity;
import com.renteasy.entity.UserEntity;
import com.renteasy.repository.InterestRepository;
import com.renteasy.repository.PropertyRepository;
import com.renteasy.repository.UserRepository;
import com.renteasy.service.InterestService;

@Service
public class InterestServiceImpl implements InterestService {

    @Autowired
    private InterestRepository interestRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void removeInterestById(Long interestId) {
        interestRepository.deleteById(interestId);
    }
    @Override
    public InterestDTO expressInterest(InterestDTO interestDTO) {
        PropertyEntity property = propertyRepository.findById(interestDTO.getPropertyId())
                .orElseThrow(() -> new RuntimeException("Property not found"));

        UserEntity buyer = userRepository.findById(interestDTO.getBuyerId())
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        if (!buyer.getRole().equals("BUYER")) {
            throw new RuntimeException("Only buyers can express interest");
        }

        if (interestRepository.existsByPropertyAndBuyer(property, buyer)) {
            throw new RuntimeException("You have already expressed interest in this property");
        }

        InterestEntity interest = new InterestEntity();
        interest.setProperty(property);
        interest.setBuyer(buyer);

        InterestEntity savedInterest = interestRepository.save(interest);
        interestDTO.setInterestId(savedInterest.getInterestId());
        return interestDTO;
    }

    @Override
    public List<UserDTO> getInterestedBuyers(Long propertyId) {
        propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found"));
        List<InterestEntity> interests = interestRepository.findByPropertyId(propertyId);
        return interests.stream()
                .map(interest -> {
                    UserDTO dto = new UserDTO();
                    dto.setUserId(interest.getBuyer().getUserId());
                    dto.setFirstName(interest.getBuyer().getFirstName());
                    dto.setLastName(interest.getBuyer().getLastName());
                    dto.setEmail(interest.getBuyer().getEmail());
                    dto.setPhone(interest.getBuyer().getPhone());
                    dto.setRole(interest.getBuyer().getRole());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    private UserDTO convertToDTO(UserEntity user) {
        UserDTO dto = new UserDTO();
        dto.setUserId(user.getUserId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole());
        return dto;
    }
 
    // In InterestServiceImpl.java
    @Override
    public List<PropertyDTO> getPropertiesInterestedByBuyer(String buyerEmail) {
        UserEntity buyer = userRepository.findByEmailIgnoreCase(buyerEmail)
            .orElseThrow(() -> new RuntimeException("Buyer not found with email: " + buyerEmail));
        List<InterestEntity> interests = interestRepository.findByBuyer(buyer);
        return interests.stream()
            .map(interest -> {
                PropertyEntity property = interest.getProperty();
                PropertyDTO dto = new PropertyDTO();
                dto.setPropertyId(property.getPropertyId());
                dto.setUserId(property.getUser().getUserId());
                dto.setTitle(property.getTitle());
                dto.setDescription(property.getDescription());
                dto.setPlace(property.getPlace());
                dto.setArea(property.getArea());
                dto.setBedrooms(property.getBedrooms());
                dto.setBathrooms(property.getBathrooms());
                dto.setHospitalsNearby(property.getHospitalsNearby());
                dto.setCollegesNearby(property.getCollegesNearby());
                dto.setRentPrice(property.getRentPrice());
                return dto;
            }).toList();
    }

   


}