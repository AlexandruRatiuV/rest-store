package com.siit.rest.store.mouse.mapper;


import com.siit.rest.store.mouse.domain.entity.MouseEntity;
import com.siit.rest.store.mouse.domain.model.MouseDtoCreateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor

public class MouseDtoPostRequestToMouseEntityMapper {



    public MouseEntity mapDtoPostRequestToEntity(MouseDtoCreateRequest dto) {
        return MouseEntity.builder()
                .id(dto.getId())
                .manufacturer(dto.getManufacturer())
                .type(dto.getType())
                             .build();
    }

}
