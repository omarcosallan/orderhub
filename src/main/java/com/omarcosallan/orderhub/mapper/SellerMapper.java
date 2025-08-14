package com.omarcosallan.orderhub.mapper;

import com.omarcosallan.orderhub.dto.*;
import com.omarcosallan.orderhub.entity.Address;
import com.omarcosallan.orderhub.entity.Seller;
import com.omarcosallan.orderhub.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SellerMapper {

    Seller toEntity(SellerDTO dto);
    Seller toEntity(SellerResponseDTO dto);
    SellerResponseDTO toDTO(Seller seller);
    Address toEntity(AddressDTO dto);
    AddressDTO toDTO(Address address);
    @Mapping(target = "active", source = "enabled")
    OwnerResponseDTO toDTO(User owner);
}
