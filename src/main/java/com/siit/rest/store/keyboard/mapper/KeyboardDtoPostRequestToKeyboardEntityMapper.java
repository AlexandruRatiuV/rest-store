package com.siit.rest.store.keyboard.mapper;


import com.siit.rest.store.keyboard.domain.entity.KeyboardEntity;
import com.siit.rest.store.keyboard.domain.model.KeyboardDtoCreateRequest;
import com.siit.rest.store.stock.mapper.StockDtoToStockEntity;
import com.siit.rest.store.stock.mapper.StockEntityToStockDto;
import lombok.*;
import org.springframework.stereotype.Component;

@Data
@Builder
@Component
public class KeyboardDtoPostRequestToKeyboardEntityMapper {

    private final StockDtoToStockEntity stockDtoToStockEntity;



    public KeyboardEntity mapDtoPostRequestToEntity(KeyboardDtoCreateRequest dto) {
        return KeyboardEntity.builder()
                .id(dto.getId())
                .manufacturer(dto.getManufacturer())
                .type(dto.getType())
                .entity(stockDtoToStockEntity.mapDtoToEntity(dto.getEntity()))
                .build();
    }

}
