package com.omarcosallan.orderhub.mapper;

import com.omarcosallan.orderhub.dto.SaleResponseDTO;
import com.omarcosallan.orderhub.entity.Sale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SaleMapper {

    @Mapping(source = "seller.id", target = "sellerId")
    @Mapping(source = "customer.id", target = "customerId")
    SaleResponseDTO toDTO(Sale entity);
}
