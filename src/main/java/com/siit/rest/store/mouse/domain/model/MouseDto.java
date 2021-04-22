package com.siit.rest.store.mouse.domain.model;


import com.siit.rest.store.stock.domain.entity.StockEntity;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Builder
public class MouseDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int model;

    private String manufacturer;

    private String type;
    private int dpi;


    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "keyboard_id")
    private StockEntity entity;


}
