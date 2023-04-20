package com.carparking.core_entity.repository;

import com.carparking.core_entity.entities.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BaseEntityRepository<T extends BaseEntity> extends JpaRepository<T, Long> {
  void deleteByIdIn(List<Long> ids);
}
