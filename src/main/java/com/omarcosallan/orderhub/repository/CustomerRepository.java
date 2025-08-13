package com.omarcosallan.orderhub.repository;

import com.omarcosallan.orderhub.entity.Customer;
import com.omarcosallan.orderhub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    boolean existsByEmail(String email);
    boolean existsByCnpj(String cnpj);

    boolean existsByOwner(User owner);
}
