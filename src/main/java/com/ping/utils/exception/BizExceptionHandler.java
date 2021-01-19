package com.ping.utils.exception;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class BizExceptionHandler {

  @ExceptionHandler(value = {MethodArgumentNotValidException.class})
  public ResponseEntity<ErrorResponse> badRequestHandler(MethodArgumentNotValidException e) {
    log.error("[MethodArgumentNotValidException] Bad request error", e);
    List<ErrorResponse.FieldError> errors =
        e.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(this::errorDetail)
            .collect(toList());
    return new ResponseEntity<>(
        error(BizError.VALIDATION_FAILED, errors), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {BizException.class})
  public ResponseEntity<ErrorResponse> bizExceptionHandler(HttpServletRequest req,
      BizException e) {
    log.error(
        "[BizException] exception happened when call url = {}, query string = {}, error code = {}, error message = {}",
        req.getRequestURI(), req.getQueryString(), e.getCode(), e.getMessage(), e);
    return new ResponseEntity<>(
        error(e.getCode(), e.getErrorDetail(), e.getMessage()),
        e.getHttpStatus());
  }

  private ErrorResponse error(BizError bizError, List<ErrorResponse.FieldError> fieldErrors) {
    return ErrorResponse.builder()
        .code(bizError.getCode())
        .message(bizError.getMessage())
        .fieldErrors(fieldErrors)
        .build();
  }

  private ErrorResponse error(Integer code, Object errorDetail, String... message) {
    return ErrorResponse.builder()
        .code(code)
        .details(errorDetail)
        .message(StringUtils.join(message, "|"))
        .build();
  }

  private ErrorResponse.FieldError errorDetail(
      org.springframework.validation.FieldError fieldError) {
    String message =
        Optional.ofNullable(fieldError)
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .orElse(Strings.EMPTY);
    String field = Optional.ofNullable(fieldError)
        .map(org.springframework.validation.FieldError::getField)
        .orElse(Strings.EMPTY);
    return ErrorResponse.FieldError.builder()
        .field(field)
        .message(message)
        .build();
  }
}
