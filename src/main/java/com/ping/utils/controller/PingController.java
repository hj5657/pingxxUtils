package com.ping.utils.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ping.utils.service.PingService;
import com.pingplusplus.exception.PingppException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class PingController {

  private final PingService pingService;

  public PingController(PingService pingService) {
    this.pingService = pingService;
  }

  @GetMapping("/ping-serial-id")
  public String getPingSerialIdByApi(@RequestParam(value = "orderId") String orderId)
      throws PingppException, JsonProcessingException {
    return pingService.getPingSerialId(orderId);
  }
}