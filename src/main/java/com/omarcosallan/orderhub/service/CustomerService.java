package com.omarcosallan.orderhub.service;

import com.omarcosallan.orderhub.dto.CustomerDTO;
import com.omarcosallan.orderhub.dto.CustomerResponseDTO;
import com.omarcosallan.orderhub.entity.Customer;
import com.omarcosallan.orderhub.exception.AlreadyExistsException;
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

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
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
        Customer customer = customerMapper.toEntity(dto);

        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new AlreadyExistsException("O cliente de e-mail " + customer.getEmail() + " já existe.");
        }
        if (customerRepository.existsByCnpj(customer.getCnpj())) {
            throw new AlreadyExistsException("O cliente de CNPJ " + customer.getCnpj() + " já existe.");
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
}
