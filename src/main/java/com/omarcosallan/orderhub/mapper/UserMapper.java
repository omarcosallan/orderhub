package com.omarcosallan.orderhub.mapper;

import com.omarcosallan.orderhub.dto.RegisterDTO;
import com.omarcosallan.orderhub.dto.UserResponseDTO;
import com.omarcosallan.orderhub.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roles", ignore = true)
    User toEntity(RegisterDTO dto);

    @Mapping(target = "active", source = "enabled")
    UserResponseDTO toDTO(User user);
}
