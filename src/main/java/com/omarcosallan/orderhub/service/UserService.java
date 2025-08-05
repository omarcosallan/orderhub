package com.omarcosallan.orderhub.service;

import com.omarcosallan.orderhub.entity.Role;
import com.omarcosallan.orderhub.entity.User;
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

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public User save(User user) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(encryptedPassword);

        Set<Role> roles = roleRepository.findByRoleIn(user.getRoles().stream().map(Role::getRole).collect(Collectors.toSet()));
        user.setRoles(roles);

        return userRepository.save(user);
    }
}
