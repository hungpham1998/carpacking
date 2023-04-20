package com.carparking.core_entity.entities;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;

@Data
@MappedSuperclass
public class BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, length = 10)
  protected Long id;

  @CreatedDate
  protected long createdAt;

  @CreatedBy
  protected String createdBy;
}
