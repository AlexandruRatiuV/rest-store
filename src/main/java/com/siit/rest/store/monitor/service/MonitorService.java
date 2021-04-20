package com.siit.rest.store.monitor.service;


import com.siit.rest.store.monitor.domain.entity.MonitorEntity;
import com.siit.rest.store.monitor.domain.model.MonitorDto;
import com.siit.rest.store.monitor.domain.model.MonitorDtoCreateRequest;
import com.siit.rest.store.monitor.mapper.MonitorDtoPostRequestToMonitorEntityMapper;
import com.siit.rest.store.monitor.domain.model.MonitorDtoUpdateRequest;
import com.siit.rest.store.monitor.mapper.MonitorDtoToMonitorEntity;
import com.siit.rest.store.monitor.repository.MonitorRepository;
import com.siit.rest.store.monitor.mapper.MonitorEntityToMonitorDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class MonitorService {

    private final MonitorRepository monitorRepository;
    private final MonitorEntityToMonitorDto monitorEntityToMonitorDto;
    private final MonitorDtoToMonitorEntity monitorDtoToMonitorEntity;
    private final MonitorDtoPostRequestToMonitorEntityMapper monitorDtoPostRequestToMonitorEntityMapper;
    private int counter;

    @Transactional(readOnly = true)
    public List<MonitorDto> getAllMonitor() {
        return monitorRepository.findAll()
                .stream()
                .map(keyEnt -> monitorEntityToMonitorDto.mapEntityToDto(keyEnt))
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<MonitorDto> getAllMonitorByManufacturer(String manufacturer) {
        return monitorRepository.findAllByManufacturer(manufacturer)
                .stream()
                .map(keyEnt -> monitorEntityToMonitorDto.mapEntityToDto(keyEnt))
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public MonitorDto getMonitorById(Integer id) {
        return monitorEntityToMonitorDto.mapEntityToDto(monitorRepository.findById(id).orElseThrow());
    }




    @Transactional(readOnly = false)
    public MonitorDto createMonitor(MonitorDto monitorDto) {
        MonitorEntity monitorEntity = monitorDtoToMonitorEntity.mapDtoToEntity(monitorDto);

        monitorRepository.save(monitorEntity);
        return monitorEntityToMonitorDto.mapEntityToDto(monitorEntity);
    }

    @Transactional(readOnly = false)
    public List<MonitorDto> createMonitors(List<MonitorDtoCreateRequest> monitorDtoCreateRequests) {
        return monitorDtoCreateRequests.stream()
                .map(keyReq ->monitorDtoPostRequestToMonitorEntityMapper.mapDtoPostRequestToEntity(keyReq))
                .map(MonitorEntity -> monitorRepository.save(MonitorEntity))
                .map(MonitorEntitySaved -> monitorEntityToMonitorDto.mapEntityToDto(MonitorEntitySaved))
                .collect(toList());

    }

    @SneakyThrows
    @Transactional(readOnly = false)
    public List<MonitorDto> createMonitorFromFile(MultipartFile file) {

        if(file.isEmpty()){

        }

        byte[] bytes = file.getBytes();
        String fileContent = new String(bytes);
        String[] rows = fileContent.split("\n");

        List<MonitorDtoCreateRequest> toCreate = new ArrayList<>();
        for(String row : rows){
            String[] rowSplitted = row.split(",");
            if (rowSplitted.length != 0) {
                MonitorDtoCreateRequest monitorDtoCreateRequest = MonitorDtoCreateRequest.builder()
                        .id(Integer.valueOf(rowSplitted[0]))
                        .manufacturer(rowSplitted[1])
                        .model(Integer.valueOf(rowSplitted[2]))
                        .type(rowSplitted[3])
                        .build();
                toCreate.add(monitorDtoCreateRequest);
            }
        }

        return createMonitors(toCreate);
    }


    @Transactional
    public MonitorDto updateMonitor(MonitorDtoUpdateRequest monitorDtoUpdateRequest) {

        Optional<MonitorEntity> byId = monitorRepository.findById(monitorDtoUpdateRequest.getId());
        MonitorEntity monitorEntity = byId.orElseThrow(() -> new RuntimeException("No entry found for given id: " + monitorDtoUpdateRequest.getId()));


        //MonitorEntity.setId(MonitorDtoUpdateRequest.getId());
        monitorEntity.setManufacturer(monitorDtoUpdateRequest.getManufacturer());
        monitorEntity.setType(monitorDtoUpdateRequest.getType());
        monitorEntity.setModel(monitorDtoUpdateRequest.getModel());


        return monitorEntityToMonitorDto.mapEntityToDto(monitorEntity);
    }

    @Transactional
    public void deleteMonitorByID(Integer id) {
        monitorRepository.deleteById(id);
    }



}
