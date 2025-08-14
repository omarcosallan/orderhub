package com.omarcosallan.orderhub.repository;

import com.omarcosallan.orderhub.entity.Seller;
import com.omarcosallan.orderhub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    boolean existsByOwner(User owner);
    boolean existsByCpf(String cpf);

    @Query("SELECT s FROM Seller s WHERE s.owner.email = :email")
    Optional<Seller> findByOwnerEmail(@Param("email") String email);
}
