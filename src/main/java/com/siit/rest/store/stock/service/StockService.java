package com.siit.rest.store.stock.service;


import com.siit.rest.store.stock.domain.entity.StockEntity;
import com.siit.rest.store.stock.domain.model.StockDto;
import com.siit.rest.store.stock.mapper.StockDtoToStockEntity;
import com.siit.rest.store.stock.mapper.StockEntityToStockDto;
import com.siit.rest.store.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class StockService {

    private final StockRepository stockRepository;
    private final StockEntityToStockDto stockEntityTostockDto;
    private final StockDtoToStockEntity stockDtoTostockEntity;


    @Transactional(readOnly = true)
    public List<StockDto> getAllstock() {
        return stockRepository.findAll()
                .stream()
                .map(keyEnt -> stockEntityTostockDto.mapEntityToDto(keyEnt))
                .collect(toList());
    }


    @Transactional(readOnly = true)
    public StockDto getstockById(Integer id) {
        return stockEntityTostockDto.mapEntityToDto(stockRepository.findById(id).orElseThrow());
    }


    @Transactional(readOnly = false)
    public StockDto createstock(StockDto stockDto) {
        StockEntity stockEntity = stockDtoTostockEntity.mapDtoToEntity(stockDto);

        stockRepository.save(stockEntity);
        return stockEntityTostockDto.mapEntityToDto(stockEntity);
    }


    @Transactional
    public void deletestockByID(Integer id) {
        stockRepository.deleteById(id);
    }


}
