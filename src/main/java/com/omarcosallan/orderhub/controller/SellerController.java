package com.omarcosallan.orderhub.controller;

import com.omarcosallan.orderhub.dto.SellerDTO;
import com.omarcosallan.orderhub.dto.SellerResponseDTO;
import com.omarcosallan.orderhub.service.SellerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sellers")
public class SellerController {

    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @GetMapping
    public ResponseEntity<List<SellerResponseDTO>> list() {
        return ResponseEntity.ok(sellerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SellerResponseDTO> customer(@PathVariable Long id) {
        return ResponseEntity.ok(sellerService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ResponseEntity<SellerResponseDTO> create(Authentication authentication, @Valid @RequestBody SellerDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sellerService.save(authentication, dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('SELLER') and @sellerService.isOwner(authentication, #id))")
    public ResponseEntity<SellerResponseDTO> update(@PathVariable Long id, @Valid @RequestBody SellerDTO dto) {
        return ResponseEntity.ok(sellerService.update(id, dto));
    }
}
