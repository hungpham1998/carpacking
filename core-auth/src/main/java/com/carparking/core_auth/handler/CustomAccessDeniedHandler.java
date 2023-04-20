package com.carparking.core_auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void handle(
			HttpServletRequest request,
			HttpServletResponse response,
			AccessDeniedException exception
	) throws IOException {

		int httpStatus = HttpStatus.FORBIDDEN.value();

		Map<String, Object> data = new HashMap<>();
		data.put("data", null);
		data.put("status", httpStatus);
		data.put("message", "Bạn không có quyền truy cập!");
		response.setStatus(httpStatus);
		response.getOutputStream().println(objectMapper.writeValueAsString(data));
	}
}