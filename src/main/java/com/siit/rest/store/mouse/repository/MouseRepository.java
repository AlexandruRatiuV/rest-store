package com.siit.rest.store.mouse.repository;


import com.siit.rest.store.mouse.domain.entity.MouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MouseRepository extends JpaRepository<MouseEntity, Integer> {
    List<MouseEntity> findAllByManufacturer(String model);

    List<MouseEntity> findAll();

}
