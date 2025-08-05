package com.omarcosallan.orderhub.mapper;

import com.omarcosallan.orderhub.dto.RegisterDTO;
import com.omarcosallan.orderhub.dto.UserResponseDTO;
import com.omarcosallan.orderhub.entity.User;

public class UserMapper {

    public static User toEntity(RegisterDTO dto) {
        return new User(null,
                dto.name(),
                dto.email(),
                dto.password(),
                null);
    }

    public static UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(user.getId(), user.getName(), user.getEmail(), user.getRoles(), user.isEnabled());
    }
}
