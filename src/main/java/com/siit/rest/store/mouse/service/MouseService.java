package com.siit.rest.store.mouse.service;


import com.siit.rest.store.mouse.domain.entity.MouseEntity;
import com.siit.rest.store.mouse.domain.model.MouseDto;
import com.siit.rest.store.mouse.domain.model.MouseDtoCreateRequest;
import com.siit.rest.store.mouse.domain.model.MouseDtoUpdateRequest;
import com.siit.rest.store.mouse.mapper.MouseDtoPostRequestToMouseEntityMapper;
import com.siit.rest.store.mouse.mapper.MouseDtoToMouseEntity;
import com.siit.rest.store.mouse.mapper.MouseEntityToMouseDto;
import com.siit.rest.store.mouse.repository.MouseRepository;
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
public class MouseService {

    private final MouseRepository mouseRepository;
    private final MouseEntityToMouseDto mouseEntityToMouseDto;
    private final MouseDtoToMouseEntity mouseDtoToMouseEntity;
    private final MouseDtoPostRequestToMouseEntityMapper mouseDtoPostRequestToMouseEntityMapper;
    private int counter;

    @Transactional(readOnly = true)
    public List<MouseDto> getAllMouse() {
        return mouseRepository.findAll()
                .stream()
                .map(keyEnt -> mouseEntityToMouseDto.mapEntityToDto(keyEnt))
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<MouseDto> getAllMouseByManufacturer(String manufacturer) {
        return mouseRepository.findAllByManufacturer(manufacturer)
                .stream()
                .map(keyEnt -> mouseEntityToMouseDto.mapEntityToDto(keyEnt))
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public MouseDto getMouseById(Integer id) {
        return mouseEntityToMouseDto.mapEntityToDto(mouseRepository.findById(id).orElseThrow());
    }


    @Transactional(readOnly = false)
    public MouseDto createMouse(MouseDto mouseDto) {
        MouseEntity mouseEntity = mouseDtoToMouseEntity.mapDtoToEntity(mouseDto);

        mouseRepository.save(mouseEntity);
        return mouseEntityToMouseDto.mapEntityToDto(mouseEntity);
    }

    @Transactional(readOnly = false)
    public List<MouseDto> createMouses(List<MouseDtoCreateRequest> mouseDtoCreateRequests) {
        return mouseDtoCreateRequests.stream()
                .map(keyReq -> mouseDtoPostRequestToMouseEntityMapper.mapDtoPostRequestToEntity(keyReq))
                .map(MouseEntity -> mouseRepository.save(MouseEntity))
                .map(MouseEntitySaved -> mouseEntityToMouseDto.mapEntityToDto(MouseEntitySaved))
                .collect(toList());

    }

    @SneakyThrows
    @Transactional(readOnly = false)
    public List<MouseDto> createMouseFromFile(MultipartFile file) {

        if (file.isEmpty()) {

        }

        byte[] bytes = file.getBytes();
        String fileContent = new String(bytes);
        String[] rows = fileContent.split("\n");

        List<MouseDtoCreateRequest> toCreate = new ArrayList<>();
        for (String row : rows) {
            String[] rowSplitted = row.split(",");
            if (rowSplitted.length != 0) {
                MouseDtoCreateRequest mouseDtoCreateRequest = MouseDtoCreateRequest.builder()
                        .id(Integer.valueOf(rowSplitted[0]))
                        .manufacturer(rowSplitted[1])
                        .model(Integer.valueOf(rowSplitted[2]))
                        .type(rowSplitted[3])
                        .build();
                toCreate.add(mouseDtoCreateRequest);
            }
        }

        return createMouses(toCreate);
    }


    @Transactional
    public MouseDto updateMouse(MouseDtoUpdateRequest mouseDtoUpdateRequest) {

        Optional<MouseEntity> byId = mouseRepository.findById(mouseDtoUpdateRequest.getId());
        MouseEntity mouseEntity = byId.orElseThrow(() -> new RuntimeException("No entry found for given id: " + mouseDtoUpdateRequest.getId()));


        //MouseEntity.setId(MouseDtoUpdateRequest.getId());
        mouseEntity.setManufacturer(mouseDtoUpdateRequest.getManufacturer());
        mouseEntity.setType(mouseDtoUpdateRequest.getType());
        mouseEntity.setModel(mouseDtoUpdateRequest.getModel());


        return mouseEntityToMouseDto.mapEntityToDto(mouseEntity);
    }

    @Transactional
    public void deleteMouseByID(Integer id) {
        mouseRepository.deleteById(id);
    }


}
