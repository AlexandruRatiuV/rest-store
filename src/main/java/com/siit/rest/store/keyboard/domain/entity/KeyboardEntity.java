package com.siit.rest.store.keyboard.domain.entity;


import com.siit.rest.store.stock.domain.model.StockDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.siit.rest.store.stock.domain.entity.StockEntity;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "keyboards")
public class KeyboardEntity {


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
