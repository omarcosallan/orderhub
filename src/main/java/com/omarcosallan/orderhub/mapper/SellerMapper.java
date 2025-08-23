package com.omarcosallan.orderhub.mapper;

import com.omarcosallan.orderhub.dto.*;
import com.omarcosallan.orderhub.entity.Address;
import com.omarcosallan.orderhub.entity.Seller;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SellerMapper {

    Seller toEntity(SellerDTO dto);
    Seller toEntity(SellerResponseDTO dto);

    @Mapping(source = "owner.name", target = "ownerName")
    @Mapping(source = "owner.email", target = "ownerEmail")
    SellerResponseDTO toDTO(Seller seller);

    Address toEntity(AddressDTO dto);
    AddressDTO toDTO(Address address);
}
