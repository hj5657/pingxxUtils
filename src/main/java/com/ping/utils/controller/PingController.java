package com.ping.utils.controller;

import com.ping.utils.model.ChargeIdResponse;
import com.ping.utils.model.OrderIdRequest;
import com.ping.utils.service.PingService;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class PingController {

  private final PingService pingService;

  public PingController(PingService pingService) {
    this.pingService = pingService;
  }

  @GetMapping("/ping-serial-id")
  public ChargeIdResponse getPingSerialIdByApi(@RequestBody @Valid OrderIdRequest request) {
    return pingService.getPingChargeId(request);
  }
}