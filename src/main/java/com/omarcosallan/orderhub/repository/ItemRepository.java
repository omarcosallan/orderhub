package com.omarcosallan.orderhub.repository;

import com.omarcosallan.orderhub.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findBySlug(String slug);
    boolean existsByNcmCode(String ncmCode);
    boolean existsBySlug(String slug);
}
