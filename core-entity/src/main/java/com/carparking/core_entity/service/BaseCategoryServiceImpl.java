package com.carparking.core_entity.service;

import com.carparking.core_entity.entities.BaseCategoryEntity;
import com.carparking.core_entity.repository.BaseCategoryRepository;
import com.carparking.core_exception.exception.ConflictException;

public class BaseCategoryServiceImpl<T extends BaseCategoryEntity>
    extends BaseEntityServiceImpl<T>
    implements BaseService<T> {

  private final BaseCategoryRepository repository;

  public BaseCategoryServiceImpl(
      BaseCategoryRepository<T> repository,
      Class<T> entityClass
  ) {
    super(repository, entityClass);
    this.repository = repository;
  }

  @Override
  public T create(T t) {
    repository.findByCode(t.getCode()).ifPresent(d -> {
      throw new ConflictException(
          entityClass.getSimpleName(), "code", t.getCode()
      );
    });

    repository.findByName(t.getName()).ifPresent(d -> {
      throw new ConflictException(
          entityClass.getSimpleName(), "name", t.getName()
      );
    });
    return super.create(t);
  }
}
