package com.gosterim360.service;

import com.gosterim360.common.MessageUtil;
import com.gosterim360.dto.request.UserRequestDTO;
import com.gosterim360.dto.response.UserResponseDTO;
import com.gosterim360.enums.Role;
import com.gosterim360.exception.UserAlreadyExistsByUsername;
import com.gosterim360.exception.UserNotFoundException;
import com.gosterim360.mapper.UserMapper;
import com.gosterim360.model.User;
import com.gosterim360.repository.UserRepository;
import com.gosterim360.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private MessageUtil messageUtil;

    private User user;
    private UserRequestDTO requestDTO;
    private UserResponseDTO responseDTO;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .username("orhanturkmenoglu")
                .email("orhan@example.com")
                .password("12345")
                .phone("5551112233")
                .roles(List.of(Role.ROLE_USER))
                .build();

        requestDTO = UserRequestDTO.builder()
                .username("orhanturkmenoglu")
                .email("orhan@example.com")
                .password("12345")
                .phone("5551112233")
                .roles(List.of(Role.ROLE_USER))
                .build();

        responseDTO = UserResponseDTO.builder()
                .id(user.getId())
                .username("orhanturkmenoglu")
                .email("orhan@example.com")
                .phone("5551112233")
                .roles(List.of(Role.ROLE_USER))
                .build();
    }

    @Test
    public void createUser_ShouldReturnResponse_WhenValidRequest() {
        when(userRepository.existsByUsername("orhanturkmenoglu")).thenReturn(false);
        when(userMapper.toEntity(requestDTO)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(responseDTO);

        UserResponseDTO result = userService.createUser(requestDTO);

        assertNotNull(result);
        assertEquals("orhanturkmenoglu", result.getUsername());
        verify(userRepository).save(user);
    }

    @Test
    public void createUser_ShouldThrowException_WhenUsernameExists() {
        when(userRepository.existsByUsername("orhanturkmenoglu")).thenReturn(true);
        when(messageUtil.getMessage("user.already.exists", "orhanturkmenoglu")).thenReturn("User already exists: orhanturkmenoglu");

        assertThrows(UserAlreadyExistsByUsername.class, () -> userService.createUser(requestDTO));

        verify(userRepository, never()).save(any());
    }

    @Test
    public void getAllUsers_ShouldReturnListOfUserResponseDTO() {
        List<User> users = List.of(user);
        List<UserResponseDTO> responseList = List.of(responseDTO);

        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toDTO(user)).thenReturn(responseDTO);

        List<UserResponseDTO> result = userService.getAllUsers();

        assertEquals(1, result.size());
        assertEquals("orhanturkmenoglu", result.get(0).getUsername());
    }

    @Test
    public void getUserById_ShouldReturnUserResponseDTO_WhenExists() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(responseDTO);

        UserResponseDTO result = userService.getUserById(user.getId());

        assertEquals("orhanturkmenoglu", result.getUsername());
    }

    @Test
    public void getUserById_ShouldThrowException_WhenNotExists() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        when(messageUtil.getMessage("user.notfound", id)).thenReturn("User not found with id: " + id);

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(id));
    }

    @Test
    public void updateUser_ShouldReturnUpdatedUser_WhenExists() {
        User updated = User.builder()
                .username("updatedUser")
                .email("updated@example.com")
                .password("newpass")
                .phone("5559998888")
                .roles(List.of(Role.ROLE_ADMIN))
                .build();

        UserRequestDTO updateDTO = UserRequestDTO.builder()
                .username("updatedUser")
                .email("updated@example.com")
                .password("newpass")
                .phone("5559998888")
                .roles(List.of(Role.ROLE_ADMIN))
                .build();

        UserResponseDTO updatedResponse = UserResponseDTO.builder()
                .id(user.getId())
                .username("updatedUser")
                .email("updated@example.com")
                .phone("5559998888")
                .roles(List.of(Role.ROLE_ADMIN))
                .build();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(updated);
        when(userMapper.toDTO(updated)).thenReturn(updatedResponse);

        UserResponseDTO result = userService.updateUser(user.getId(), updateDTO);

        assertEquals("updatedUser", result.getUsername());
        assertEquals("updated@example.com", result.getEmail());
    }

    @Test
    public void updateUser_ShouldThrowException_WhenUserNotFound() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        when(messageUtil.getMessage("user.notfound", id)).thenReturn("User not found with id: " + id);

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(id, requestDTO));
    }

    @Test
    public void deleteUserById_ShouldDeleteUser_WhenExists() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        userService.deleteUserById(user.getId());

        verify(userRepository).delete(user);
    }

    @Test
    public void deleteUserById_ShouldThrowException_WhenNotFound() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        when(messageUtil.getMessage("user.notfound", id)).thenReturn("User not found with id: " + id);

        assertThrows(UserNotFoundException.class, () -> userService.deleteUserById(id));
    }
}
