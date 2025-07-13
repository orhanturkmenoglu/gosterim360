package com.gosterim360.model;


import com.gosterim360.common.BaseEntity;
import com.gosterim360.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity<UUID> {


    @Column(nullable = false,unique = true)
    private String username;

    private String password;

    private String email;

    private String phone;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="user_roles",joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private List<Role> roles;
}
