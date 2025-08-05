package com.omarcosallan.orderhub.dto;

import com.omarcosallan.orderhub.entity.Role;

import java.util.Set;

public record UserResponseDTO(Long id, String name, String email, Set<Role> roles, Boolean active) {
}
