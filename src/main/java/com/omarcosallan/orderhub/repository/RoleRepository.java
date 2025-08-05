package com.omarcosallan.orderhub.repository;

import com.omarcosallan.orderhub.entity.Role;
import com.omarcosallan.orderhub.entity.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Set<Role> findByRoleIn(Collection<RoleType> roles);
}
