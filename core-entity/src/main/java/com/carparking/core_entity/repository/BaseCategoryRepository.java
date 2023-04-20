package com.carparking.core_entity.repository;

import com.carparking.core_entity.entities.BaseCategoryEntity;

import java.util.Optional;

public interface BaseCategoryRepository<T extends BaseCategoryEntity> extends BaseEntityRepository<T> {
  Optional<T> findByCode(String code);
  Optional<T> findByName(String name);
}
