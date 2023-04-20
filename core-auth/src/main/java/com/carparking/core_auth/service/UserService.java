package com.carparking.core_auth.service;

import com.carparking.core_auth.model.User;




public interface UserService {
  User findByUsernameToLogin(String username);

  User findById(String userId, boolean withAdditionalData);
}
