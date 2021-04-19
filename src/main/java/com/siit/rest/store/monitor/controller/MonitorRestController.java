package com.siit.rest.store.monitor.controller;


import com.siit.rest.store.monitor.domain.model.MonitorDto;
import com.siit.rest.store.monitor.service.MonitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/monitors")
public class MonitorRestController {

    private final MonitorService monitorService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MonitorDto getMonitorById(@PathVariable(name = "id") Integer monitorId) {
        return monitorService.getMonitorById(monitorId);

    }

    @GetMapping(value = "?manufacturer={manufacturer}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MonitorDto> getAllMonitorByManufacturer(@PathVariable(name = "manufacturer") String manufacturer) {
        return monitorService.getAllMonitorByManufacturer(manufacturer);

    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MonitorDto> getAllMonitor() {
        return monitorService.getAllMonitor();
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MonitorDto createMonitor(MonitorDto monitorDto) {
        return monitorService.createMonitor(monitorDto);
    }


}
