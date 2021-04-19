package com.siit.rest.store.monitor.service;


import com.siit.rest.store.monitor.domain.entity.MonitorEntity;
import com.siit.rest.store.monitor.domain.model.MonitorDto;
import com.siit.rest.store.monitor.mapper.MonitorDtoToMonitorEntity;
import com.siit.rest.store.monitor.mapper.MonitorEntityToMonitorDto;
import com.siit.rest.store.monitor.repository.MonitorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MonitorService {

    private final MonitorRepository monitorRepository;
    private final MonitorEntityToMonitorDto monitorEntityToMonitorDto;
    private final MonitorDtoToMonitorEntity monitorDtoToMonitorEntity;
    private int counter;

    public List<MonitorDto> getAllMonitor() {
        return monitorRepository.findAll()
                .stream()
                .map(keyEnt -> monitorEntityToMonitorDto.mapEntityToDto(keyEnt))
                .collect(Collectors.toList());
    }

    public List<MonitorDto> getAllMonitorByManufacturer(String manufacturer) {
        return monitorRepository.findAllByManufacturer(manufacturer)
                .stream()
                .map(keyEnt -> monitorEntityToMonitorDto.mapEntityToDto(keyEnt))
                .collect(Collectors.toList());
    }

    public MonitorDto getMonitorById(Integer id) {
        return monitorEntityToMonitorDto.mapEntityToDto(monitorRepository.findById(id).orElseThrow());
    }




    public MonitorDto createMonitor(MonitorDto monitorDto) {
        MonitorEntity monitorEntity = monitorDtoToMonitorEntity.mapDtoToEntity(monitorDto);

        monitorRepository.save(monitorEntity);
        return monitorEntityToMonitorDto.mapEntityToDto(monitorEntity);
    }



}
