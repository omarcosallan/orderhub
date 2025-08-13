package com.omarcosallan.orderhub.service;

import com.omarcosallan.orderhub.dto.CustomerDTO;
import com.omarcosallan.orderhub.dto.CustomerResponseDTO;
import com.omarcosallan.orderhub.entity.Customer;
import com.omarcosallan.orderhub.entity.User;
import com.omarcosallan.orderhub.exception.AlreadyExistsException;
import com.omarcosallan.orderhub.exception.BadRequestException;
import com.omarcosallan.orderhub.exception.ResourceNotFoundException;
import com.omarcosallan.orderhub.mapper.CustomerMapper;
import com.omarcosallan.orderhub.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final AuthService authService;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper, AuthService authService) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.authService = authService;
    }

    @Transactional(readOnly = true)
    public List<CustomerResponseDTO> findAll() {
        return customerRepository.findAll().stream().map(customerMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public CustomerResponseDTO findById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + id));
        return customerMapper.toDTO(customer);
    }

    @Transactional
    public CustomerResponseDTO save(CustomerDTO dto) {
        if (customerRepository.existsByEmail(dto.email())) {
            throw new AlreadyExistsException("O cliente de e-mail " + dto.email() + " já existe.");
        }
        if (customerRepository.existsByCnpj(dto.cnpj())) {
            throw new AlreadyExistsException("O cliente de CNPJ " + dto.cnpj() + " já existe.");
        }

        Customer customer = customerMapper.toEntity(dto);
        if (customer.getOwner() == null) {
            User owner = authService.authenticated();
            if (customerRepository.existsByOwner(owner)) {
                throw new BadRequestException("Este usuário '" + owner.getEmail() + "' já está associado a um Cliente.");
            }
            customer.setOwner(owner);
        }
        customerRepository.save(customer);

        return customerMapper.toDTO(customer);
    }

    @Transactional
    public CustomerResponseDTO update(Long id, CustomerDTO dto) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + id));

        if (!existingCustomer.getEmail().equals(dto.email())) {
            if (customerRepository.existsByEmail(dto.email())) {
                throw new AlreadyExistsException("O e-mail '" + dto.email() + "' já está cadastrado para outro cliente.");
            }
            existingCustomer.setEmail(dto.email());
        }
        if (!existingCustomer.getCnpj().equals(dto.cnpj())) {
            if (customerRepository.existsByCnpj(dto.cnpj())) {
                throw new AlreadyExistsException("O CNPJ '" + dto.cnpj() + "' já está cadastrado para outro cliente.");
            }
            existingCustomer.setCnpj(dto.cnpj());
        }

        existingCustomer.setCompanyName(dto.companyName());
        existingCustomer.setPhone(dto.phone());
        existingCustomer.setAddress(customerMapper.toEntity(dto.address()));

        customerRepository.save(existingCustomer);

        return customerMapper.toDTO(existingCustomer);
    }

    public boolean isOwner(Long id) {
        User authenticatedUser = authService.authenticated();
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + id));
        return customer.getOwner().getId().equals(authenticatedUser.getId());
    }
}
