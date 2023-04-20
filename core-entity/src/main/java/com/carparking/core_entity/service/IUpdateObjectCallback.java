package com.carparking.core_entity.service;

import com.carparking.core_entity.entities.BaseDocument;

public interface IUpdateObjectCallback<T extends BaseDocument>{
  public void updateObject(T document);
}
