package com.omarcosallan.orderhub.service;

import com.omarcosallan.orderhub.dto.SellerDTO;
import com.omarcosallan.orderhub.dto.SellerResponseDTO;
import com.omarcosallan.orderhub.entity.Seller;
import com.omarcosallan.orderhub.entity.User;
import com.omarcosallan.orderhub.exception.AlreadyExistsException;
import com.omarcosallan.orderhub.exception.BadRequestException;
import com.omarcosallan.orderhub.exception.ResourceNotFoundException;
import com.omarcosallan.orderhub.mapper.SellerMapper;
import com.omarcosallan.orderhub.repository.SellerRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerService {

    private final SellerRepository sellerRepository;
    private final UserService userService;
    private final SellerMapper sellerMapper;

    public SellerService(SellerRepository sellerRepository, UserService userService, SellerMapper sellerMapper) {
        this.sellerRepository = sellerRepository;
        this.userService = userService;
        this.sellerMapper = sellerMapper;
    }

    public List<SellerResponseDTO> findAll() {
        return sellerRepository.findAll().stream().map(sellerMapper::toDTO).toList();
    }

    public SellerResponseDTO findById(Long id) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendedor não encontrado com id: " + id));
        return sellerMapper.toDTO(seller);
    }


    public Seller findByOwnerEmail(String email) {
        return sellerRepository.findByOwnerEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Vendedor não encontrado com e-mail: " + email));
    }

    public SellerResponseDTO save(Authentication authentication, SellerDTO dto) {
        if (sellerRepository.existsByCpf(dto.cpf())) {
            throw new AlreadyExistsException("O vendedor de CPF " + dto.cpf() + " já existe.");
        }

        User owner;
        if (dto.ownerId() != null) {
            owner = userService.findById(dto.ownerId());
        } else {
            owner = userService.findByEmail(authentication.getName());
        }

        if (sellerRepository.existsByOwner(owner)) {
            throw new BadRequestException("Este usuário '" + owner.getEmail() + "' já está associado a um Vendedor.");
        }

        Seller seller = sellerMapper.toEntity(dto);
        seller.setOwner(owner);

        sellerRepository.save(seller);

        return sellerMapper.toDTO(seller);
    }

    public SellerResponseDTO update(Long id, SellerDTO dto) {
        Seller existingSeller = sellerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendedor não encontrado com id: " + id));

        if (!existingSeller.getCpf().equals(dto.cpf())) {
            if (sellerRepository.existsByCpf(dto.cpf())) {
                throw new AlreadyExistsException("O CPF '" + dto.cpf() + "' já está cadastrado para outro vendedor.");
            }
            existingSeller.setCpf(dto.cpf());
        }

        existingSeller.setName(dto.name());
        existingSeller.setPhone(dto.phone());
        existingSeller.setAddress(sellerMapper.toEntity(dto.address()));

        sellerRepository.save(existingSeller);

        return sellerMapper.toDTO(existingSeller);
    }

    public boolean isOwner(Authentication authentication, Long id) {
        String authenticatedUserEmail = authentication.getName();
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendedor não encontrado com id: " + id));
        return seller.getOwner().getEmail().equals(authenticatedUserEmail);
    }
}
