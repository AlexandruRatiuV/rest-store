package com.siit.rest.store.monitor.mapper;



import com.siit.rest.store.monitor.domain.entity.MonitorEntity;
import com.siit.rest.store.monitor.domain.model.MonitorDtoCreateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor

public class MonitorDtoPostRequestToMonitorEntityMapper {



    public MonitorEntity mapDtoPostRequestToEntity(MonitorDtoCreateRequest dto) {
        return MonitorEntity.builder()
                .id(dto.getId())
                .manufacturer(dto.getManufacturer())
                .type(dto.getType())
                             .build();
    }

}
