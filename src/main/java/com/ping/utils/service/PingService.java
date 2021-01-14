package com.ping.utils.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.PingppException;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.ChargeCollection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PingService {

  private final static String privateKeyFilePath = "src/main/resources/ping-key.pem";
  @Value("${pingApp.apiKey}")
  private String apiKey;
  @Value("${pingApp.appId}")
  private String appId;

  public String getPingSerialId(String requestOrderId)
      throws PingppException, JsonProcessingException {
    Pingpp.privateKeyPath = privateKeyFilePath;
    Pingpp.apiKey = apiKey;
    Map<String, Object> chargeParams = new HashMap<>();
    chargeParams.put("limit", 100);
    chargeParams.put("app[id]", appId);
    ChargeCollection chargeList = Charge.list(chargeParams);
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode = objectMapper.readTree(chargeList.toString());
    JsonNode list = jsonNode.get("data");
    for (JsonNode item : list) {
      String orderId = item.findValue("body").toString().split(" ")[1].substring(0, 9);
      if (orderId.equals(requestOrderId)) {
        return item.findValue("id").toString();
      }
    }
    return "sorry, do not find for request order id:" + requestOrderId;
  }
}