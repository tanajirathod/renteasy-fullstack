package com.renteasy.service;

import com.renteasy.dto.UserDTO;

public interface UserService {
    UserDTO registerUser(UserDTO userDTO);
    UserDTO loginUser(String email, String password);
    UserDTO getUserByEmail(String email);
}
