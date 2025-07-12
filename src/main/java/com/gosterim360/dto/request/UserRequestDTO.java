package com.gosterim360.dto.request;

import com.gosterim360.enums.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@Schema(name = "UserRequestDTO", description = "Request DTO for creating or updating a user")
public class UserRequestDTO {

    private String username;
    private String password;
    private String email;
    private String phone;
    private List<Role> roles; // Role enum (ör: ROLE_ADMIN, ROLE_USER)
}
