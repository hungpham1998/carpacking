package com.carparking.application.controller;


//import com.carparking.application.request.LoginRequest;
//import com.carparking.application.response.JwtAuthenticationResponse;
//import com.carparking.core_auth.jwt.JwtTokenProvider;
//import com.carparking.core_response_advice.config.EnableWrappedResponse;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetails;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.Valid;
//
//@CrossOrigin(origins = "*")
//@EnableWrappedResponse
//@RestController
//@RequestMapping("/api/auth")
//@Slf4j
//public class AuthController {
//
//    @Autowired
//    AuthenticationManager authenticationManager;
//
//    @Autowired
//    JwtTokenProvider jwtTokenProvider;
//
//    @PostMapping("/login")
//    public JwtAuthenticationResponse authenticateUser(
//            @RequestBody @Valid LoginRequest loginRequest,
//            HttpServletRequest request
//    ) {
//
//        UsernamePasswordAuthenticationToken token =
//                new UsernamePasswordAuthenticationToken(
//                        loginRequest.getUsername().toLowerCase(),
//                        loginRequest.getPassword()
//                );
//        token.setDetails(new WebAuthenticationDetails(request));
//
//        Authentication authentication =
//                authenticationManager.authenticate(token);
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        String jwt = jwtTokenProvider.generateToken(authentication);
//        return new JwtAuthenticationResponse(jwt);
//    }
//}


import com.carparking.application.request.LoginRequest;
import com.carparking.application.response.JwtAuthenticationResponse;
import com.carparking.core_auth.jwt.JwtTokenProvider;
import com.carparking.core_response_advice.config.EnableWrappedResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@CrossOrigin(origins = "*")
@EnableWrappedResponse
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public JwtAuthenticationResponse authenticateUser(
            @RequestBody @Valid LoginRequest loginRequest,
            HttpServletRequest request
    ) {

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername().toLowerCase(),
                        loginRequest.getPassword()
                );

        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.generateToken(authentication);
        return new JwtAuthenticationResponse(jwt);
    }
}

