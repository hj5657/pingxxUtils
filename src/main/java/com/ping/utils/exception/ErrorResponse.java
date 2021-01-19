package com.ping.utils.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@AllArgsConstructor
@Builder
public class ErrorResponse implements Serializable {

  private static final long serialVersionUID = 5922507987325369682L;

  private Integer code;
  private String message;
  @Builder.Default
  private List<FieldError> fieldErrors = new ArrayList<>();
  private Object details;

  @Builder
  @Getter
  public static class FieldError {

    private String field;
    private String message;

    @Override
    public String toString() {
      return String.format("%s:%s", field, message);
    }
  }
}
