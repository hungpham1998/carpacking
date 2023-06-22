package com.carparking.core_entity.entities;

import com.carparking.core_utils.annotation.DontUpdate;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Data
public class BaseDocument {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
}
