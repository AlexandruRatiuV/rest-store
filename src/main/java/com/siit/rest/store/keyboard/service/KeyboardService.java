package com.siit.rest.store.keyboard.service;

import com.siit.rest.store.keyboard.domain.entity.KeyboardEntity;
import com.siit.rest.store.keyboard.domain.model.KeyboardDto;
import com.siit.rest.store.keyboard.mapper.KeyboardDtoToKeyboardEntity;
import com.siit.rest.store.keyboard.mapper.KeyboardEntityToKeyboardDto;
import com.siit.rest.store.keyboard.repository.KeyboardRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class KeyboardService {

    private final KeyboardRepository keyboardRepository;
    private final KeyboardEntityToKeyboardDto keyboardEntityToKeyboardDto;
    private final KeyboardDtoToKeyboardEntity keyboardDtoToKeyboardEntity;
    private int counter;

    public List<KeyboardDto> getAllKeyboard() {
        return keyboardRepository.findAll()
                .stream()
                .map(keyEnt -> keyboardEntityToKeyboardDto.mapEntityToDto(keyEnt))
                .collect(Collectors.toList());
    }

    public List<KeyboardDto> getAllKeyboardByManufacturer(String manufacturer) {
        return keyboardRepository.findAllByManufacturer(manufacturer)
                .stream()
                .map(keyEnt -> keyboardEntityToKeyboardDto.mapEntityToDto(keyEnt))
                .collect(Collectors.toList());
    }

    public KeyboardDto getKeyboardById(Integer id) {
        return keyboardEntityToKeyboardDto.mapEntityToDto(keyboardRepository.findById(id).orElseThrow());
    }




    public KeyboardDto createKeyboard(KeyboardDto keyboardDto) {
        KeyboardEntity keyboardEntity = keyboardDtoToKeyboardEntity.mapDtoToEntity(keyboardDto);

        keyboardRepository.save(keyboardEntity);
        return keyboardEntityToKeyboardDto.mapEntityToDto(keyboardEntity);
    }



}
