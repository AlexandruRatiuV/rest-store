package com.siit.rest.store.keyboard.controller;

import com.siit.rest.store.keyboard.domain.entity.KeyboardEntity;
import com.siit.rest.store.keyboard.service.KeyboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/keyboard")
public class KeyboardRestController {

    private final KeyboardService keyboardService;

    @GetMapping(value = "/{keyboardId}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public KeyboardEntity getKeyboardByID(@PathVariable(name = "temp") Integer keyboardId){
        return keyboardService.getKeyboardById(keyboardId);

    }


    @GetMapping( produces = MediaType.APPLICATION_JSON_VALUE)
    public List<KeyboardEntity> getAllKeyboard() {
        return keyboardService.getAllKeyboard();
    }

}
