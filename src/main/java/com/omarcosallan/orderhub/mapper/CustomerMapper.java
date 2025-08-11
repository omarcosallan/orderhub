package com.omarcosallan.orderhub.mapper;

import com.omarcosallan.orderhub.dto.AddressDTO;
import com.omarcosallan.orderhub.dto.CustomerDTO;
import com.omarcosallan.orderhub.dto.CustomerResponseDTO;
import com.omarcosallan.orderhub.entity.Address;
import com.omarcosallan.orderhub.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer toEntity(CustomerDTO dto);
    CustomerResponseDTO toDTO(Customer entity);
    Address toEntity(AddressDTO dto);
    AddressDTO toDTO(Address address);
}
