package com.siit.rest.store.stock.controller;


import com.siit.rest.store.stock.domain.model.StockDto;

import com.siit.rest.store.stock.service.StockService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/stock")
public class StockRestController {

    private final StockService stockService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StockDto getstockById(@PathVariable(name = "id") Integer stockId) {
        return stockService.getstockById(stockId);

    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<StockDto> getAllstock() {
        return stockService.getAllstock();
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StockDto createstock(StockDto stockDto) {
        return stockService.createstock(stockDto);
    }


}
