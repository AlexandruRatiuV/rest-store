package com.siit.rest.store.monitor.mapper;


import com.siit.rest.store.monitor.domain.entity.MonitorEntity;
import com.siit.rest.store.monitor.domain.model.MonitorDto;
import org.springframework.stereotype.Component;

@Component
public class MonitorEntityToMonitorDto {

    public MonitorDto mapEntityToDto(MonitorEntity entity) {
        return MonitorDto.builder()
                .id(entity.getId())
                .manufacturer(entity.getManufacturer())
                .model(entity.getModel())
                .type(entity.getType())
                .build();
    }
}
