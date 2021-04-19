package com.siit.rest.store.mouse.mapper;


import com.siit.rest.store.mouse.domain.entity.MouseEntity;
import com.siit.rest.store.mouse.domain.model.MouseDto;
import org.springframework.stereotype.Component;

@Component
public class MouseEntityToMouseDto {

    public MouseDto mapEntityToDto(MouseEntity entity) {
        return MouseDto.builder()
                .id(entity.getId())
                .manufacturer(entity.getManufacturer())
                .model(entity.getModel())
                .type(entity.getType())
                .build();
    }
}
