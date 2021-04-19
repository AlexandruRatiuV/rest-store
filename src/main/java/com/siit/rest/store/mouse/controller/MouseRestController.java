package com.siit.rest.store.mouse.controller;



import com.siit.rest.store.mouse.domain.model.MouseDto;
import com.siit.rest.store.mouse.service.MouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/mouses")
public class MouseRestController {

    private final MouseService mouseService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MouseDto getMouseById(@PathVariable(name = "id") Integer MouseId) {
        return mouseService.getMouseById(MouseId);

    }

    @GetMapping(value = "?manufacturer={manufacturer}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MouseDto> getAllMouseByManufacturer(@PathVariable(name = "manufacturer") String manufacturer) {
        return mouseService.getAllMouseByManufacturer(manufacturer);

    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MouseDto> getAllMouse() {
        return mouseService.getAllMouse();
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MouseDto createMouse(MouseDto MouseDto) {
        return mouseService.createMouse(MouseDto);
    }


}
