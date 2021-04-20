package com.siit.rest.store.keyboard.repository;

import com.siit.rest.store.keyboard.domain.entity.KeyboardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KeyboardRepository extends JpaRepository<KeyboardEntity, Integer> {
    List<KeyboardEntity> findAllByManufacturer (String model);

    List<KeyboardEntity> findAll();

}
