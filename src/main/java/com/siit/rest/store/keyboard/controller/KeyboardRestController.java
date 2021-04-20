package com.siit.rest.store.keyboard.controller;

import com.siit.rest.store.keyboard.domain.model.KeyboardDto;
import com.siit.rest.store.keyboard.service.KeyboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/keyboards")
public class KeyboardRestController {

    private final KeyboardService keyboardService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public KeyboardDto getKeyboardByID(@PathVariable(name = "id") Integer keyboardId) {
        return keyboardService.getKeyboardById(keyboardId);

    }

    @GetMapping(value = "?manufacturer={manufacturer}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<KeyboardDto> getAllKeyboardByManufacturer(@PathVariable(name = "manufacturer") String manufacturer) {
        return keyboardService.getAllKeyboardByManufacturer(manufacturer);

    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<KeyboardDto> getAllKeyboard() {
        return keyboardService.getAllKeyboard();
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public KeyboardDto createKeyboard(KeyboardDto keyboardDto) {
        return keyboardService.createKeyboard(keyboardDto);
    }

    @PostMapping("/csv-upload")
    public List<KeyboardDto> createKeyboardFromFile(@RequestParam(name = "csv-file") MultipartFile file){
        return keyboardService.createKeyboardFromFile(file);
    }

    @DeleteMapping(value = "/{keyboardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteKeyboardByID(@PathVariable(name = "keyboardId") Integer ID){
        keyboardService.deleteKeyboardByID(ID);
    }


}
