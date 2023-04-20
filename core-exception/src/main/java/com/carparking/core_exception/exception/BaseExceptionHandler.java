package com.carparking.core_exception.exception;

import com.carparking.core_response_advice.model.WrappedResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;

@ControllerAdvice
@Slf4j
public class BaseExceptionHandler extends ResponseEntityExceptionHandler {

  @Autowired
  MessageSource messageSource;

  @ExceptionHandler(RunException.class)
  public ResponseEntity<?> handleRunException(RunException ex) {
    log.error("Error occurred: {}", getStackTrace(ex));

    String message = ex.getCode();
    try {
      message = messageSource.getMessage(
          ex.getCode(), null, LocaleContextHolder.getLocale()
      );
      for (Map.Entry<String, Object> param : ex.getParams().entrySet()) {
        message = message.replace(
            "%" + param.getKey() + "%",
            String.valueOf(param.getValue())
        );
      }
    } catch (NoSuchMessageException ignore) {
      log.warn("(handleRunException)code: {} --> CANNOT RESOLVE", ex.getCode());
    }

    WrappedResponse errorResponse = new WrappedResponse(
        null, message, ex.getStatus()
    );
    return new ResponseEntity<>(
        errorResponse, HttpStatus.valueOf(errorResponse.getStatus())
    );
  }
}
