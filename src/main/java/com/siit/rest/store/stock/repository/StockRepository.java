package com.siit.rest.store.stock.repository;


import com.siit.rest.store.mouse.domain.entity.MouseEntity;
import com.siit.rest.store.stock.domain.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, Integer> {

    List<StockEntity> findAll();

}
