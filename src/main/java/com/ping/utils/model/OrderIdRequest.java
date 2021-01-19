package com.ping.utils.model;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderIdRequest {
  @NotBlank(message = "order id could not be empty")
  String orderId;
}
