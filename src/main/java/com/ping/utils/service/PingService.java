package com.ping.utils.service;

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
      throws PingppException {
    Pingpp.privateKeyPath = privateKeyFilePath;
    Pingpp.apiKey = apiKey;
    Map<String, Object> chargeParams = new HashMap<>();
    chargeParams.put("limit", 100);
    chargeParams.put("app[id]", appId);
    ChargeCollection chargeList = Charge.list(chargeParams);
    for (Charge item : chargeList.getData()) {
      String orderId = item.getBody().split(" ")[1];
      if (orderId.equals(requestOrderId)) {
        return item.getId();
      }
    }
    return "sorry, do not find for request order id:" + requestOrderId;
  }
}