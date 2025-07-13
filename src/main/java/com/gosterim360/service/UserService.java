package com.gosterim360.service;

import com.gosterim360.dto.request.UserRequestDTO;
import com.gosterim360.dto.response.UserResponseDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO requestDTO);

    List<UserResponseDTO>  getAllUsers();

    UserResponseDTO getUserById(UUID id);

    UserResponseDTO updateUser(UUID id, UserRequestDTO requestDTO);

    void deleteUserById(UUID id);
}
