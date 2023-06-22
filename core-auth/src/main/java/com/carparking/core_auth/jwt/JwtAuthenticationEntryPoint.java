package com.carparking.core_auth.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void commence(
			HttpServletRequest httpServletRequest,
			HttpServletResponse response,
			AuthenticationException authException
	) throws IOException {
		logger.error(
				"Responding with unauthorized error. url: {}, message: {}",
				httpServletRequest.getRequestURL(),
				authException.getMessage()
		);

		HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
		response.setStatus(httpStatus.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		String message;
		if (authException.getCause() != null) {
			message = authException.getCause().toString() + " " + authException.getMessage();
		} else {
			message = authException.getMessage();
		}

		Map<String, Object> data = new HashMap<>();
		data.put("status", httpStatus.value());
		data.put("message", message);
		response.getOutputStream().println(objectMapper.writeValueAsString(data));
	}
}
