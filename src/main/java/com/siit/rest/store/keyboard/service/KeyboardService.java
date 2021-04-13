package com.siit.rest.store.keyboard.service;

import com.siit.rest.store.keyboard.domain.entity.KeyboardEntity;
import com.siit.rest.store.keyboard.repository.KeyboardRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class KeyboardService {

    private final KeyboardRepository keyboardRepository;

    public List<KeyboardEntity> getAllKeyboard() {
        return keyboardRepository.findAll();
    }

    public KeyboardEntity getKeyboardById(Integer id) {
        return keyboardRepository.findById(id).orElseThrow();
    }

}
