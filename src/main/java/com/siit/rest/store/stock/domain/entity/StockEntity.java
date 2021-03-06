package com.siit.rest.store.stock.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "stock")
public class StockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private Integer price;

    private Integer quantity;


    @JoinColumn(name = "keyboard_id", referencedColumnName = "id")
    private int keyboard_id;


    @JoinColumn(name = "monitor_id", referencedColumnName = "id")
    private int monitor_id;


    @JoinColumn(name = "mouse_id", referencedColumnName = "id")
    private int mouse_id;


}
