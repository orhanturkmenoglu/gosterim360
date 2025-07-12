package com.gosterim360.service.impl;

import com.gosterim360.common.MessageUtil;
import com.gosterim360.dto.request.UserRequestDTO;
import com.gosterim360.dto.response.UserResponseDTO;
import com.gosterim360.exception.UserAlreadyExistsByUsername;
import com.gosterim360.exception.UserNotFoundException;
import com.gosterim360.mapper.UserMapper;
import com.gosterim360.model.User;
import com.gosterim360.repository.UserRepository;
import com.gosterim360.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MessageUtil messageUtil;

    @Override
    public UserResponseDTO createUser(UserRequestDTO requestDTO) {
        log.info("UserServiceImpl::createUser started");

        if (userRepository.existsByUsername(requestDTO.getUsername())) {
            log.error("UserServiceImpl::createUser failed - User already exists");
            throw new UserAlreadyExistsByUsername(messageUtil.getMessage("user.already.exists", requestDTO.getUsername()));
        }

        User user = userMapper.toEntity(requestDTO);
        User savedUser = userRepository.save(user);

        log.info("UserServiceImpl::createUser completed - User ID: {}", savedUser.getId());
        return userMapper.toDTO(savedUser);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        log.info("UserServiceImpl::getAllUsers started");

        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Override
    public UserResponseDTO getUserById(UUID id) {
        log.info("UserServiceImpl::getUserById started - ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(messageUtil.getMessage("user.notfound", id)));
        return userMapper.toDTO(user);
    }

    @Override
    public UserResponseDTO updateUser(UUID id, UserRequestDTO requestDTO) {
        log.info("UserServiceImpl::updateUser started - ID: {}", id);

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(messageUtil.getMessage("user.notfound", id)));

        existingUser.setUsername(requestDTO.getUsername());
        existingUser.setEmail(requestDTO.getEmail());
        existingUser.setPhone(requestDTO.getPhone());
        existingUser.setPassword(requestDTO.getPassword());
        existingUser.setRoles(requestDTO.getRoles());

        User updatedUser = userRepository.save(existingUser);

        log.info("UserServiceImpl::updateUser completed - ID: {}", id);
        return userMapper.toDTO(updatedUser);
    }

    @Override
    public void deleteUserById(UUID id) {
        log.info("UserServiceImpl::deleteUserById started - ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(messageUtil.getMessage("user.notfound", id)));

        userRepository.delete(user);

        log.info("UserServiceImpl::deleteUserById completed - ID: {}", id);
    }
}
