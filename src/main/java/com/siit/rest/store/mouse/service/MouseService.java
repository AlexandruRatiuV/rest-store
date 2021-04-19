package com.siit.rest.store.mouse.service;


import com.siit.rest.store.mouse.domain.entity.MouseEntity;
import com.siit.rest.store.mouse.domain.model.MouseDto;
import com.siit.rest.store.mouse.mapper.MouseDtoToMouseEntity;
import com.siit.rest.store.mouse.mapper.MouseEntityToMouseDto;
import com.siit.rest.store.mouse.repository.MouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MouseService {

    private final MouseRepository mouseRepository;
    private final MouseEntityToMouseDto mouseEntityToMouseDto;
    private final MouseDtoToMouseEntity mouseDtoToMouseEntity;
    private int counter;

    public List<MouseDto> getAllMouse() {
        return mouseRepository.findAll()
                .stream()
                .map(keyEnt -> mouseEntityToMouseDto.mapEntityToDto(keyEnt))
                .collect(Collectors.toList());
    }

    public List<MouseDto> getAllMouseByManufacturer(String manufacturer) {
        return mouseRepository.findAllByManufacturer(manufacturer)
                .stream()
                .map(keyEnt -> mouseEntityToMouseDto.mapEntityToDto(keyEnt))
                .collect(Collectors.toList());
    }

    public MouseDto getMouseById(Integer id) {
        return mouseEntityToMouseDto.mapEntityToDto(mouseRepository.findById(id).orElseThrow());
    }




    public MouseDto createMouse(MouseDto MouseDto) {
        MouseEntity MouseEntity = mouseDtoToMouseEntity.mapDtoToEntity(MouseDto);

        mouseRepository.save(MouseEntity);
        return mouseEntityToMouseDto.mapEntityToDto(MouseEntity);
    }



}
