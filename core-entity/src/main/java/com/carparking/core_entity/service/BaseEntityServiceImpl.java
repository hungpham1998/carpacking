package com.carparking.core_entity.service;

import com.carparking.core_entity.entities.BaseEntity;
import com.carparking.core_entity.repository.BaseEntityRepository;
import com.carparking.core_exception.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Slf4j
public class BaseEntityServiceImpl<T extends BaseEntity>
    implements BaseService<T> {

  private final BaseEntityRepository<T> repository;
  protected final Class<T> entityClass;

  public BaseEntityServiceImpl(
      BaseEntityRepository<T> repository,
      Class<T> entityClass
      ) {
    this.repository = repository;
    this.entityClass = entityClass;
  }

  @Override
  public T create(T t) {
    return repository.save(t);
  }

  @Override
  public List<T> create(List<T> list) {
    return repository.saveAll(list);
  }

  @Override
  public void delete(Long id) {
    repository.deleteById(id);
  }

  @Override
  public void delete(List<Long> ids) {
    repository.deleteByIdIn(ids);
  }

  @Override
  public List<T> findAll() {
    return repository.findAll();
  }

  @Override
  public Page<T> findAll(Pageable page) {
    return repository.findAll(page);
  }

  @Override
  public T findById(Long id, boolean errorIfNotFound) {
    Optional<T> t = repository.findById(id);
    if (!t.isPresent() && errorIfNotFound) {
      throw new NotFoundException(id, entityClass.getName());
    }

    return t.orElse(null);
  }

  @Override
  public List<T> findByIds(List<Long> ids) {
    return repository.findAllById(ids);
  }
}
