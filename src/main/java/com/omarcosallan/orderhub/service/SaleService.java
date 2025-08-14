package com.omarcosallan.orderhub.service;

import com.omarcosallan.orderhub.dto.SaleDTO;
import com.omarcosallan.orderhub.dto.SaleItemDTO;
import com.omarcosallan.orderhub.dto.SaleResponseDTO;
import com.omarcosallan.orderhub.entity.*;
import com.omarcosallan.orderhub.exception.BadRequestException;
import com.omarcosallan.orderhub.exception.ResourceNotFoundException;
import com.omarcosallan.orderhub.mapper.SaleMapper;
import com.omarcosallan.orderhub.mapper.SellerMapper;
import com.omarcosallan.orderhub.repository.CustomerRepository;
import com.omarcosallan.orderhub.repository.ItemRepository;
import com.omarcosallan.orderhub.repository.SaleRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final CustomerRepository customerRepository;
    private final ItemRepository itemRepository;
    private final SellerService sellerService;
    private final SaleMapper saleMapper;
    private final SellerMapper sellerMapper;

    public SaleService(SaleRepository saleRepository, CustomerRepository customerRepository, ItemRepository itemRepository, SellerService sellerService, SaleMapper saleMapper, SellerMapper sellerMapper) {
        this.saleRepository = saleRepository;
        this.customerRepository = customerRepository;
        this.itemRepository = itemRepository;
        this.sellerService = sellerService;
        this.saleMapper = saleMapper;
        this.sellerMapper = sellerMapper;
    }

    @Transactional(readOnly = true)
    public List<SaleResponseDTO> findAll() {
        return saleRepository.findAll()
                .stream().map(saleMapper::toDTO).toList();
    }

    @Transactional(readOnly = true)
    public SaleResponseDTO findById(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venda não encontrada com id: " + id));
        return saleMapper.toDTO(sale);
    }

    @Transactional
    public SaleResponseDTO save(Authentication authentication, SaleDTO dto) {
        Customer customer = customerRepository.findById(dto.customerId())
                .orElseThrow(() -> new ResourceNotFoundException("Venda inválida. Cliente não encontrado com id: " + dto.customerId()));

        Seller seller;
        if (dto.sellerId() != null) {
            seller = sellerMapper.toEntity(sellerService.findById(dto.sellerId()));
        } else {
            seller = sellerService.findByOwnerEmail(authentication.getName());
        }

        Sale sale = new Sale();
        sale.setSeller(seller);
        sale.setCustomer(customer);
        sale.setStatus(SaleStatus.PENDING_PAYMENT);

        Set<SaleItem> items = new HashSet<>();

        for (SaleItemDTO itemDTO : dto.items()) {
            Item item = itemRepository.findById(itemDTO.itemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Venda inválida. Item não encontrado com id: " + itemDTO.itemId()));

            if (item.getStockQuantity() < itemDTO.quantity()) {
                throw new BadRequestException("Venda inválida. Item não disponível em estoque.");
            }

            Integer quantity = itemDTO.quantity();
            BigDecimal unitPrice = item.getPrice();
            BigDecimal total = unitPrice.multiply(BigDecimal.valueOf(quantity));

            Double discount = itemDTO.discount();
            if (discount != null && discount > 0) {
                BigDecimal discountRate = BigDecimal.valueOf(discount).divide(BigDecimal.valueOf(100));
                BigDecimal discountAmount = total.multiply(discountRate);
                total = total.subtract(discountAmount);
            }

            BigDecimal subTotal = total.setScale(2, RoundingMode.HALF_UP);

            SaleItem saleItem = new SaleItem(sale, item, itemDTO.quantity(), itemDTO.discount(), subTotal);

            items.add(saleItem);
        }

        sale.setItems(items);
        BigDecimal totalAmount = sale.getItems().stream()
                .map(SaleItem::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
        sale.setTotalAmount(totalAmount);

        saleRepository.save(sale);

        return saleMapper.toDTO(sale);
    }

    public boolean isOwner(Authentication authentication, Long id) {
        String email = authentication.getName();
        return saleRepository.findById(id)
                .map(sale -> sale.getSeller().getOwner().getEmail().equals(email))
                .orElse(false);
    }

    public boolean isCustomer(Authentication authentication, Long id) {
        String email = authentication.getName();
        return saleRepository.findById(id)
                .map(sale -> sale.getCustomer().getEmail().equals(email))
                .orElse(false);
    }
}
