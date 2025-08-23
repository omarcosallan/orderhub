package com.omarcosallan.orderhub.mapper;

import com.omarcosallan.orderhub.dto.AddressDTO;
import com.omarcosallan.orderhub.dto.CustomerDTO;
import com.omarcosallan.orderhub.dto.CustomerResponseDTO;
import com.omarcosallan.orderhub.entity.Address;
import com.omarcosallan.orderhub.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer toEntity(CustomerDTO dto);

    @Mapping(source = "owner.name", target = "ownerName")
    @Mapping(source = "owner.email", target = "ownerEmail")
    CustomerResponseDTO toDTO(Customer entity);

    Address toEntity(AddressDTO dto);
    AddressDTO toDTO(Address address);
}
