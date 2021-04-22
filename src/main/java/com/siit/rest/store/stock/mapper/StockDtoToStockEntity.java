package com.siit.rest.store.stock.mapper;


import com.siit.rest.store.stock.domain.entity.StockEntity;
import com.siit.rest.store.stock.domain.model.StockDto;
import org.springframework.stereotype.Component;

@Component
public class StockDtoToStockEntity {

    public StockEntity mapDtoToEntity(StockDto entity) {
        return StockEntity.builder()
                .id(entity.getId())
                .price(entity.getPrice())
                .quantity(entity.getQuantity())
                .build();
    }
}
