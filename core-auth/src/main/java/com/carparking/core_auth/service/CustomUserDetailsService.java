package com.carparking.core_auth.service;


import com.carparking.core_auth.exception.UnauthorizedException;
import com.carparking.core_auth.model.User;
import com.carparking.core_auth.model.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) {
    User user = userService.findByUsernameToLogin(username);
    if (Objects.isNull(user)) {
      throw new UsernameNotFoundException("Không tìm thấy username: " + username);
    }

    if (user.isDelete()) {
      throw new UnauthorizedException("Tài khoản đã bị khóa. username: " + username);
    }

    return UserPrincipal.create(user);
  }
}
