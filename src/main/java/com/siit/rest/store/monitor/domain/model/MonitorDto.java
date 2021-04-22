package com.siit.rest.store.monitor.domain.model;


import com.siit.rest.store.stock.domain.entity.StockEntity;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Builder
public class MonitorDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int model;

    private String manufacturer;

    private String type;
    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "keyboard_id")
    private StockEntity entity;


}
