package com.siit.rest.store.monitor.mapper;


import com.siit.rest.store.monitor.domain.entity.MonitorEntity;
import com.siit.rest.store.monitor.domain.model.MonitorDto;
import org.springframework.stereotype.Component;

@Component
public class MonitorDtoToMonitorEntity {

    public MonitorEntity mapDtoToEntity(MonitorDto entity) {
        return MonitorEntity.builder()
                .id(entity.getId())
                .manufacturer(entity.getManufacturer())
                .model(entity.getModel())
                .type(entity.getType())
                .build();
    }
}
