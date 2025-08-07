package com.omarcosallan.orderhub.mapper;

import com.omarcosallan.orderhub.dto.ItemDTO;
import com.omarcosallan.orderhub.dto.ItemResponseDTO;
import com.omarcosallan.orderhub.entity.Item;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMapper {

    Item toEntity(ItemDTO dto);
    ItemResponseDTO toDTO(Item item);
}
