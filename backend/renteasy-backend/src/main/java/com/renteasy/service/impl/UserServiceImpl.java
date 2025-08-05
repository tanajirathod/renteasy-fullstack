package com.renteasy.service.impl;

import com.renteasy.dto.UserDTO;
import com.renteasy.entity.UserEntity;
import com.renteasy.repository.UserRepository;
import com.renteasy.service.UserService;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDTO registerUser(UserDTO userDTO) {
        if (userRepository.findByEmailIgnoreCase(userDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        UserEntity user = modelMapper.map(userDTO, UserEntity.class);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        UserEntity savedUser = userRepository.save(user);
        UserDTO responseDTO = modelMapper.map(savedUser, UserDTO.class);
        responseDTO.setPassword(null);
        return responseDTO;
    }

    @Override
    public UserDTO loginUser(String email, String password) {
        UserEntity user = userRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        if (password != null && !passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        userDTO.setPassword(null);
        return userDTO;
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        Optional<UserEntity> userEntityOpt = userRepository.findByEmailIgnoreCase(email);
        if (userEntityOpt.isEmpty()) {
            throw new RuntimeException("User not found with email: " + email);
        }
        UserEntity userEntity = userEntityOpt.get();

        UserDTO userDTO = modelMapper.map(userEntity, UserDTO.class);
        userDTO.setPassword(null);
        return userDTO;
    }
}
