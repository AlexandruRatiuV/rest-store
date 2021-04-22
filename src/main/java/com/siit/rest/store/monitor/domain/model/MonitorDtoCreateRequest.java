package com.siit.rest.store.monitor.domain.model;

import com.siit.rest.store.stock.domain.entity.StockEntity;
import com.siit.rest.store.stock.domain.model.StockDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MonitorDtoCreateRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int model;

    private String manufacturer;

    private String type;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "keyboard_id")
    private StockDto entity;



}
