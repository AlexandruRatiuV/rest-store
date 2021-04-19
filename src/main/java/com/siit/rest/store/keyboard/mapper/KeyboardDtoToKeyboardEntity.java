package com.siit.rest.store.keyboard.mapper;

import com.siit.rest.store.keyboard.domain.entity.KeyboardEntity;
import com.siit.rest.store.keyboard.domain.model.KeyboardDto;
import org.springframework.stereotype.Component;

@Component
public class KeyboardDtoToKeyboardEntity {

    public KeyboardEntity mapDtoToEntity(KeyboardDto keyboardDto) {
        return KeyboardEntity.builder()
                .id(keyboardDto.getId())
                .manufacturer(keyboardDto.getManufacturer())
                .model(keyboardDto.getModel())
                .type(keyboardDto.getType())
                .build();
    }
}
