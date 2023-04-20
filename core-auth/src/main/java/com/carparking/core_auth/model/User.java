package com.carparking.core_auth.model;

import java.util.List;
import java.util.Map;

public interface User {
  Long getId();
  String getUsername();
  String getPassword();
  boolean isDelete();
  List<? extends Role> getRoles();
  Map<String, Object> getAdditionalData();
}
