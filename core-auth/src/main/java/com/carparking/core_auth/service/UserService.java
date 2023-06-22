package com.carparking.core_auth.service;

import com.carparking.core_auth.model.User;

public interface UserService {
  User findByUsernameToLogin(String username);

  User findById(Long userId, boolean withAdditionalData);
  User findById(Long userId);

}
