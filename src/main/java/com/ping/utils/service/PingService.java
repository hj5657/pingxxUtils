package com.ping.utils.service;

import static com.pingplusplus.Pingpp.apiKey;

import com.ping.utils.config.PingxxProperties;
import com.ping.utils.model.ChargeIdResponse;
import com.ping.utils.model.OrderIdRequest;
import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.PingppException;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.ChargeCollection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class PingService {

  private final static String privateKeyFilePath = "src/main/resources/ping-key.pem";
  private final PingxxProperties pingxxProperties;

  public PingService(PingxxProperties pingxxProperties) {
    this.pingxxProperties = pingxxProperties;
  }

  public ChargeIdResponse getPingChargeId(OrderIdRequest request)
      throws PingppException {
    Pingpp.privateKeyPath = privateKeyFilePath;
    apiKey = pingxxProperties.getApiKey();
    Map<String, Object> chargeParams = new HashMap<>();
    chargeParams.put("limit", pingxxProperties.getLimit());
    chargeParams.put("app[id]", pingxxProperties.getAppId());
    ChargeCollection chargeList = Charge.list(chargeParams);
    for (Charge item : chargeList.getData()) {
      if (item.getOrderNo().startsWith(request.getOrderId())) {
        String firstMatchChargeId = item.getId();
        return ChargeIdResponse.builder().chargeId(firstMatchChargeId)
            .build();
      }
    }
    throw new IllegalArgumentException();
  }
}