package com.omarcosallan.orderhub.service;

import com.omarcosallan.orderhub.dto.ItemDTO;
import com.omarcosallan.orderhub.dto.ItemResponseDTO;
import com.omarcosallan.orderhub.entity.Item;
import com.omarcosallan.orderhub.exception.AlreadyExistsException;
import com.omarcosallan.orderhub.exception.BadRequestException;
import com.omarcosallan.orderhub.exception.ResourceNotFoundException;
import com.omarcosallan.orderhub.mapper.ItemMapper;
import com.omarcosallan.orderhub.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public ItemService(ItemRepository itemRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.itemMapper = itemMapper;
    }

    @Transactional(readOnly = true)
    public List<ItemResponseDTO> findAll() {
        return itemRepository.findAll().stream().map(itemMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public ItemResponseDTO findBySlug(String slug) {
        Item item = itemRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado com slug: " + slug));
        return itemMapper.toDTO(item);
    }

    @Transactional
    public ItemResponseDTO save(ItemDTO dto) {
        Item item = itemMapper.toEntity(dto);

        if (itemRepository.existsByNcmCode(item.getNcmCode())) {
            throw new AlreadyExistsException("O item de código " + item.getNcmCode() + " já existe.");
        }
        if (item.getPrice().compareTo(BigDecimal.ZERO) < 0.0) {
            throw new BadRequestException("Preço do item não pode ser menor que zero.");
        }
        if (item.getStockQuantity() < 1) {
            throw new BadRequestException("A quantidade em estoque deve ser maior ou igual a 1");
        }

        itemRepository.save(item);

        return itemMapper.toDTO(item);
    }

    @Transactional
    public void delete(String slug) {
        Item item = itemRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado com slug: " + slug));
        itemRepository.delete(item);
    }
}
