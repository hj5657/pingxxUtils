package com.ping.utils.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BizError {
  //server
  VALIDATION_FAILED(101000000, "validation failed"),
  //charge
  CHARGE_ID_NOT_FOUND_BY_ORDER_ID(102000000, "charge id not found by order id %s"),
  //pingxx
  PING_SERVER_ERROR(103000000, "ping server error: %s");
  private int code;
  private String message;
}
