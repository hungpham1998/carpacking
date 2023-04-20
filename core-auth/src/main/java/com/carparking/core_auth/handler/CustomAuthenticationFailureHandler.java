package com.carparking.core_auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
										AuthenticationException exception) throws IOException, ServletException {
		HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
		Map<String, Object> data = new HashMap<>();
		data.put("code", httpStatus.value());
		data.put("status", httpStatus.name());
		data.put("message", "Bạn không có quyền truy cập!");
		response.setStatus(httpStatus.value());
		response.getOutputStream().println(objectMapper.writeValueAsString(data));
	}
}
