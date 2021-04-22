package com.siit.rest.store.mouse.mapper;


import com.siit.rest.store.mouse.domain.entity.MouseEntity;
import com.siit.rest.store.mouse.domain.model.MouseDtoCreateRequest;
import com.siit.rest.store.stock.mapper.StockDtoToStockEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Builder
@Component
public class MouseDtoPostRequestToMouseEntityMapper {


    private final StockDtoToStockEntity stockDtoToStockEntity;

    public MouseEntity mapDtoPostRequestToEntity(MouseDtoCreateRequest dto) {
        return MouseEntity.builder()
                .id(dto.getId())
                .manufacturer(dto.getManufacturer())
                .type(dto.getType())
                .entity(stockDtoToStockEntity.mapDtoToEntity(dto.getEntity()))
                .build();
    }

}
