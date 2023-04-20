package com.carparking.core_entity.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public class BaseCategoryEntity extends BaseEntity {

  @Column(unique = true)
  protected String code;

  @Column(unique = true)
  protected String name;
}
