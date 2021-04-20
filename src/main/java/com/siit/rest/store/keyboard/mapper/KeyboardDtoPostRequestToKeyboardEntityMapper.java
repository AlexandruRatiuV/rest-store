package com.siit.rest.store.keyboard.mapper;


import com.siit.rest.store.keyboard.domain.entity.KeyboardEntity;
import com.siit.rest.store.keyboard.domain.model.KeyboardDtoCreateRequest;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KeyboardDtoPostRequestToKeyboardEntityMapper {



    public KeyboardEntity mapDtoPostRequestToEntity(KeyboardDtoCreateRequest dto) {
        return KeyboardEntity.builder()
                .id(dto.getId())
                .manufacturer(dto.getManufacturer())
                .type(dto.getType())
                             .build();
    }

}
