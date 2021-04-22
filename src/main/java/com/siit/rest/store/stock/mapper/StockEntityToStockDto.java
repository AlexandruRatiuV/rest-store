package com.siit.rest.store.stock.mapper;


import com.siit.rest.store.stock.domain.entity.StockEntity;
import com.siit.rest.store.stock.domain.model.StockDto;
import org.springframework.stereotype.Component;

@Component
public class StockEntityToStockDto {

    public StockDto mapEntityToDto(StockEntity entity) {
        return StockDto.builder()
                .id(entity.getId())
                .price(entity.getPrice())
                .quantity(entity.getQuantity())
                .build();
    }
}
