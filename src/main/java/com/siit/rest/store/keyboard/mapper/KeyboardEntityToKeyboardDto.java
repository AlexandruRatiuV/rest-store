package com.siit.rest.store.keyboard.mapper;

import com.siit.rest.store.keyboard.domain.entity.KeyboardEntity;
import com.siit.rest.store.keyboard.domain.model.KeyboardDto;
import org.springframework.stereotype.Component;

@Component
public class KeyboardEntityToKeyboardDto {

    public KeyboardDto mapEntityToDto(KeyboardEntity entity) {
        return KeyboardDto.builder()
                .id(entity.getId())
                .manufacturer(entity.getManufacturer())
                .model(entity.getModel())
                .type(entity.getType())
                .build();
    }
}
