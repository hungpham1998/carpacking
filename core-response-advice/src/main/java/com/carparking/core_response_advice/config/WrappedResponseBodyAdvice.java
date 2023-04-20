package com.carparking.core_response_advice.config;

import com.carparking.core_response_advice.model.ResponseObject;
import com.carparking.core_response_advice.model.ResponseObjectType;
import com.carparking.core_response_advice.model.WrappedListResponse;
import com.carparking.core_response_advice.model.WrappedResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@ConditionalOnProperty(
    name = "application.format-response.enable",
    havingValue = "true"
)
@ControllerAdvice
@Slf4j
public class WrappedResponseBodyAdvice implements ResponseBodyAdvice<Object> {

  @Value("${application.format-response.exclude-paths:/uploads**}")
  private List<String> excludePaths;

  @Override
  public boolean supports(
      MethodParameter returnType,
      Class<? extends HttpMessageConverter<?>> converterType
  ) {
    return
        returnType.hasMethodAnnotation(EnableWrappedResponse.class)
        || Objects.nonNull(
            returnType.getContainingClass()
                      .getAnnotation(EnableWrappedResponse.class)
        );
  }

  @Override
  public Object beforeBodyWrite(
      Object body,
      MethodParameter returnType,
      MediaType selectedContentType,
      Class<? extends HttpMessageConverter<?>> selectedConverterType,
      ServerHttpRequest request,
      ServerHttpResponse response
  ) {
    String requestedPath = request.getURI().getPath();
    log.info("(beforeBodyWrite)requestedPath: {}", requestedPath);
    if (body instanceof WrappedResponse) {
      return body;
    }

    for (String path : excludePaths) {
      if (path.endsWith("**")) {
        String prefix = path.substring(0, path.indexOf('*'));
        if (requestedPath.startsWith(prefix)) {
          return body;
        }
      } else if (Objects.equals(requestedPath, path)) {
        return body;
      }
    }
    String message = "Thao tác thành công!";
    if (request.getMethod() == HttpMethod.GET) {
      message = "Lấy dữ liệu thành công!";
    }
    if (body instanceof ResponseObject) {
      ResponseObject obj = (ResponseObject) body;
      return new WrappedListResponse(
          obj.getData(), obj.getMeta(), message, HttpStatus.OK.value()
      );
    } else if (body instanceof ResponseObjectType) {
      ResponseObjectType obj = (ResponseObjectType) body;
      return new WrappedListResponse(
              obj.getData(), obj.getMeta(), message, HttpStatus.OK.value()
      );
    }
    else if(body instanceof Map){
      return new WrappedResponse(((Map<?, ?>) body).get("data"), message, HttpStatus.OK.value());
    }
    return new WrappedResponse(body, message, HttpStatus.OK.value());
  }
}
