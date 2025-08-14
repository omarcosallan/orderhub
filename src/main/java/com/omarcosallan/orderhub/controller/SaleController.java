package com.omarcosallan.orderhub.controller;

import com.omarcosallan.orderhub.dto.SaleDTO;
import com.omarcosallan.orderhub.dto.SaleResponseDTO;
import com.omarcosallan.orderhub.service.SaleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') " +
            "or (hasRole('ROLE_SELLER') and @saleService.isOwner(authentication, #id))" +
            "or (hasRole('ROLE_CUSTOMER') and @saleService.isCustomer(authentication, #id))")
    public ResponseEntity<SaleResponseDTO> one(@PathVariable Long id) {
        return ResponseEntity.ok(saleService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ResponseEntity<SaleResponseDTO> create(Authentication authentication, @RequestBody SaleDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(saleService.save(authentication, dto));
    }
}
