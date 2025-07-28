package com.gosterim360.dto.response;

import com.gosterim360.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@Schema(name = "UserResponseDTO", description = "Response DTO for user data")
public class UserResponseDTO {

    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private List<Role> roles;
    private Instant createdAt;
    private Instant updatedAt;
}
