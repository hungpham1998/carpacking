package com.carparking.application.exception;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler
//		implements ResponseBodyAdvice<Map<String, Object>>
{

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		LOGGER.error("Error Occurred.", ex.getMessage());
		ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), HttpStatus.NOT_FOUND, null);
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	};

	@ExceptionHandler(ResourceAlreadyExistsException.class)
	public ResponseEntity<?> resourceAlreadyExistsException(ResourceAlreadyExistsException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), HttpStatus.CONFLICT, null);
		return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
	};

	@ExceptionHandler(SQLException.class)
	public ResponseEntity<?> databaseError(SQLException ex) {
		LOGGER.error("Error SQLException.", ex.getMessage());
		ErrorDetails errorDetails = new ErrorDetails("Lỗi hệ thống!", HttpStatus.INTERNAL_SERVER_ERROR, null);
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	};

	@ExceptionHandler(DataAccessException.class)
	public ResponseEntity<?> databaseDataAccessException(DataAccessException ex) {
		LOGGER.error("Error DataAccessException.", ex.getMessage());
		ErrorDetails errorDetails = new ErrorDetails("Lỗi hệ thống!", HttpStatus.INTERNAL_SERVER_ERROR, null);
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	};

	@ExceptionHandler(AuthenticationServiceException.class)
	public ResponseEntity<?> AuthenticationException(AuthenticationServiceException ex, WebRequest request) {
		LOGGER.error("Error Occurred.", ex);
		ErrorDetails errorDetails = new ErrorDetails("Bạn không có quyền truy cập!", HttpStatus.UNAUTHORIZED);
		return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
	};

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> AccessDeniedException(AccessDeniedException ex, WebRequest request) {
		LOGGER.error("Error Occurred.", ex.getMessage());
		return new ResponseEntity<>(HttpStatus.FORBIDDEN);
	};

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
		LOGGER.error("Error Occurred.", ex.getMessage());
		ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	};

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> globleEntityHandler(EntityNotFoundException ex, WebRequest request) {
		LOGGER.error("Error Occurred.", ex.getMessage());
		ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), HttpStatus.NOT_FOUND, null);
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	};

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, Object> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
		});
		errors.put("status", 400);
		LOGGER.error("Error Occurred.", errors);
		return new ResponseEntity<Object>(errors, HttpStatus.PRECONDITION_FAILED);
	};

//	@Override
//	public boolean supports(final MethodParameter methodParameter,
//			final Class<? extends HttpMessageConverter<?>> aClass) {
//		return !aClass.equals(StringHttpMessageConverter.class);
//	};
//
//	@Override
//	public Map<String, Object> beforeBodyWrite(Map<String, Object> body, MethodParameter returnType,
//			MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType,
//			ServerHttpRequest request, ServerHttpResponse response) {
//		if (request.getMethodValue().equals("GET")) {
//			final ObjectMapper mapper = new ObjectMapper();
//			final ObjectDetailResponse dataGet = mapper.convertValue(body, ObjectDetailResponse.class);
//			if (dataGet.getData().size() == 0) {
//				Map<String, Object> map = new HashMap<>();
//				map.put("message", "Không có dữ liệu !");
//				map.put("status", HttpStatus.NO_CONTENT);
//				response.setStatusCode(HttpStatus.NO_CONTENT);
//				return map;
//			} else {
//				return body;
//			}
//		} else {
//			return body;
//		}
//	};

}
