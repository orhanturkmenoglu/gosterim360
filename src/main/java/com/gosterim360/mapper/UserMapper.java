package com.gosterim360.mapper;

import com.gosterim360.common.BaseMapper;
import com.gosterim360.dto.request.UserRequestDTO;
import com.gosterim360.dto.response.UserResponseDTO;
import com.gosterim360.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends BaseMapper<User, UserResponseDTO, UserRequestDTO> {


    @Override
    public User toEntity(UserRequestDTO request) {
        if (request == null) return null;

        return User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .phone(request.getPhone())
                .roles(request.getRoles())
                .build();
    }

    @Override
    public UserResponseDTO toDTO(User entity) {
        if (entity == null) return null;

        return UserResponseDTO.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .roles(entity.getRoles())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }


}
