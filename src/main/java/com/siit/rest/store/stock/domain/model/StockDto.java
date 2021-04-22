package com.siit.rest.store.stock.domain.model;


import com.siit.rest.store.stock.domain.entity.StockEntity;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Builder
public class StockDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int price;

    private int quantity;

    @OneToOne
    @JoinColumn(name = "keyboard_id", referencedColumnName = "id")
    private int keyboard_id;

    @OneToOne
    @JoinColumn(name = "monitor_id", referencedColumnName = "id")
    private int monitor_id;

    @OneToOne
    @JoinColumn(name = "mouse_id", referencedColumnName = "id")
    private int mouse_id;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "keyboard_id")
    private StockEntity entity;


}
