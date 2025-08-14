package com.omarcosallan.orderhub.service;

import com.omarcosallan.orderhub.dto.RegisterDTO;
import com.omarcosallan.orderhub.dto.UserResponseDTO;
import com.omarcosallan.orderhub.entity.Role;
import com.omarcosallan.orderhub.entity.RoleType;
import com.omarcosallan.orderhub.entity.User;
import com.omarcosallan.orderhub.exception.AlreadyExistsException;
import com.omarcosallan.orderhub.exception.BadRequestException;
import com.omarcosallan.orderhub.exception.ResourceNotFoundException;
import com.omarcosallan.orderhub.mapper.UserMapper;
import com.omarcosallan.orderhub.repository.RoleRepository;
import com.omarcosallan.orderhub.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + id));
    }

    @Transactional
    public UserResponseDTO save(RegisterDTO dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new AlreadyExistsException("O e-mail " + dto.email() + " já está em uso.");
        }

        User user = userMapper.toEntity(dto);

        String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encryptedPassword);

        try {
            if (dto.roles().isEmpty()) {
                Role role = roleRepository.findByRole(RoleType.SELLER)
                        .orElseThrow(() -> new BadRequestException("A role informada é inválida."));
                user.setRoles(Set.of(role));
            } else {
                Set<Role> roles = roleRepository.findByRoleIn(dto.roles().stream().map(RoleType::valueOf).collect(Collectors.toSet()));
                user.setRoles(roles);
            }
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("Uma ou mais roles informadas são inválidas.");
        }

        userRepository.save(user);

        return userMapper.toDTO(user);
    }
}
