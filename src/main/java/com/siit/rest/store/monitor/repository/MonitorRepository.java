package com.siit.rest.store.monitor.repository;


import com.siit.rest.store.monitor.domain.entity.MonitorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonitorRepository extends JpaRepository<MonitorEntity, Integer> {
    List<MonitorEntity> findAllByManufacturer (String model);

    List<MonitorEntity> findAll();

}
