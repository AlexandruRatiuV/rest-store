package com.siit.rest.store.keyboard.service;

import com.siit.rest.store.keyboard.domain.entity.KeyboardEntity;
import com.siit.rest.store.keyboard.domain.model.KeyboardDto;
import com.siit.rest.store.keyboard.domain.model.KeyboardDtoCreateRequest;
import com.siit.rest.store.keyboard.mapper.KeyboardDtoPostRequestToKeyboardEntityMapper;
import com.siit.rest.store.keyboard.domain.model.KeyboardDtoUpdateRequest;
import com.siit.rest.store.keyboard.mapper.KeyboardDtoToKeyboardEntity;
import com.siit.rest.store.keyboard.mapper.KeyboardEntityToKeyboardDto;
import com.siit.rest.store.keyboard.repository.KeyboardRepository;
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
public class KeyboardService {

    private final KeyboardRepository keyboardRepository;
    private final KeyboardEntityToKeyboardDto keyboardEntityToKeyboardDto;
    private final KeyboardDtoToKeyboardEntity keyboardDtoToKeyboardEntity;
    private final KeyboardDtoPostRequestToKeyboardEntityMapper keyboardDtoPostRequestToKeyboardEntityMapper;
    private int counter;

    @Transactional(readOnly = true)
    public List<KeyboardDto> getAllKeyboard() {
        return keyboardRepository.findAll()
                .stream()
                .map(keyEnt -> keyboardEntityToKeyboardDto.mapEntityToDto(keyEnt))
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<KeyboardDto> getAllKeyboardByManufacturer(String manufacturer) {
        return keyboardRepository.findAllByManufacturer(manufacturer)
                .stream()
                .map(keyEnt -> keyboardEntityToKeyboardDto.mapEntityToDto(keyEnt))
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public KeyboardDto getKeyboardById(Integer id) {
        return keyboardEntityToKeyboardDto.mapEntityToDto(keyboardRepository.findById(id).orElseThrow());
    }




    @Transactional(readOnly = false)
    public KeyboardDto createKeyboard(KeyboardDto keyboardDto) {
        KeyboardEntity keyboardEntity = keyboardDtoToKeyboardEntity.mapDtoToEntity(keyboardDto);

        keyboardRepository.save(keyboardEntity);
        return keyboardEntityToKeyboardDto.mapEntityToDto(keyboardEntity);
    }

    @Transactional(readOnly = false)
    public List<KeyboardDto> createKeyboards(List<KeyboardDtoCreateRequest> keyboardDto) {
        return keyboardDto.stream()
                .map(keyReq ->keyboardDtoPostRequestToKeyboardEntityMapper.mapDtoPostRequestToEntity(keyReq))
                .map(keyboardEntity -> keyboardRepository.save(keyboardEntity))
                .map(keyboardEntitySaved -> keyboardEntityToKeyboardDto.mapEntityToDto(keyboardEntitySaved))
                .collect(toList());

    }

    @SneakyThrows
    @Transactional(readOnly = false)
    public List<KeyboardDto> createKeyboardFromFile(MultipartFile file) {

        if(file.isEmpty()){

        }

        byte[] bytes = file.getBytes();
        String fileContent = new String(bytes);
        String[] rows = fileContent.split("\n");

        List<KeyboardDtoCreateRequest> toCreate = new ArrayList<>();
        for(String row : rows){
            String[] rowSplitted = row.split(",");
            if (rowSplitted.length != 0) {
                KeyboardDtoCreateRequest keyboardDtoCreateRequest = KeyboardDtoCreateRequest.builder()
                        .id(Integer.valueOf(rowSplitted[0]))
                        .manufacturer(rowSplitted[1])
                        .model(Integer.valueOf(rowSplitted[2]))
                        .type(rowSplitted[3])
                        .build();
                toCreate.add(keyboardDtoCreateRequest);
            }
        }

        return createKeyboards(toCreate);
    }


    @Transactional
    public KeyboardDto updateKeyboard(KeyboardDtoUpdateRequest keyboardDtoUpdateRequest) {

        Optional<KeyboardEntity> byId = keyboardRepository.findById(keyboardDtoUpdateRequest.getId());
        KeyboardEntity keyboardEntity = byId.orElseThrow(() -> new RuntimeException("No entry found for given id: " + keyboardDtoUpdateRequest.getId()));


        //keyboardEntity.setId(keyboardDtoUpdateRequest.getId());
        keyboardEntity.setManufacturer(keyboardDtoUpdateRequest.getManufacturer());
        keyboardEntity.setType(keyboardDtoUpdateRequest.getType());
        keyboardEntity.setModel(keyboardDtoUpdateRequest.getModel());


        return keyboardEntityToKeyboardDto.mapEntityToDto(keyboardEntity);
    }

    @Transactional
    public void deleteKeyboardByID(Integer id) {
        keyboardRepository.deleteById(id);
    }



}
