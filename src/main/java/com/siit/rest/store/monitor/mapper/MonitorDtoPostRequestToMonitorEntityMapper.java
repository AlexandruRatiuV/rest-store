package com.siit.rest.store.monitor.mapper;



import com.siit.rest.store.monitor.domain.entity.MonitorEntity;
import com.siit.rest.store.monitor.domain.model.MonitorDtoCreateRequest;
import com.siit.rest.store.stock.mapper.StockDtoToStockEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Builder
@Component
public class MonitorDtoPostRequestToMonitorEntityMapper {

    private final StockDtoToStockEntity stockDtoToStockEntity;

    public MonitorEntity mapDtoPostRequestToEntity(MonitorDtoCreateRequest dto) {
        return MonitorEntity.builder()
                .id(dto.getId())
                .manufacturer(dto.getManufacturer())
                .type(dto.getType())
                .entity(stockDtoToStockEntity.mapDtoToEntity(dto.getEntity()))
                             .build();
    }

}
