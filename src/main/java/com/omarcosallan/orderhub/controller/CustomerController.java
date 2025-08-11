package com.omarcosallan.orderhub.controller;

import com.omarcosallan.orderhub.dto.CustomerDTO;
import com.omarcosallan.orderhub.dto.CustomerResponseDTO;
import com.omarcosallan.orderhub.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> list() {
        return ResponseEntity.ok(customerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> customer(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> create(@Valid @RequestBody CustomerDTO customer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.save(customer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CustomerDTO customer) {
        return ResponseEntity.ok(customerService.update(id, customer));
    }
}
