package com.carparking.core_entity.service;

import com.carparking.core_entity.entities.BaseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BaseService<T extends BaseEntity> {
  T create(T t);

  List<T> create(List<T> list);

  void delete(Long id);

  void delete(List<Long> ids);

  List<T> findAll();

  Page<T> findAll(Pageable page);

  T findById(Long id, boolean errorIfNotFound);

  List<T> findByIds(List<Long> ids);
}
