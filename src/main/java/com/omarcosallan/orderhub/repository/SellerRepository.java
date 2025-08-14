package com.omarcosallan.orderhub.repository;

import com.omarcosallan.orderhub.entity.Seller;
import com.omarcosallan.orderhub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    boolean existsByOwner(User owner);
    boolean existsByCpf(String cpf);
}
