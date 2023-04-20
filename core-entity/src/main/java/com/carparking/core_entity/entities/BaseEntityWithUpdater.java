package com.carparking.core_entity.entities;

import lombok.Data;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public class BaseEntityWithUpdater extends BaseEntity {
  @LastModifiedDate
  protected Long updatedAt;

  @LastModifiedBy
  protected String updatedBy;
}
