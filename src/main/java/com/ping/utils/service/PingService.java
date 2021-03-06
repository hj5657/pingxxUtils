package com.ping.utils.service;

import com.ping.utils.config.PingxxProperties;
import com.ping.utils.exception.BizError;
import com.ping.utils.exception.BizException;
import com.ping.utils.model.ChargeIdResponse;
import com.ping.utils.model.OrderIdRequest;
import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.PingppException;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.ChargeCollection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class PingService {

  private final PingxxProperties pingxxProperties;

  public PingService(PingxxProperties pingxxProperties) {
    this.pingxxProperties = pingxxProperties;
  }

  public ChargeIdResponse getPingChargeId(OrderIdRequest request) {
    Pingpp.privateKeyPath = pingxxProperties.getPrivateKeyFilePath();
    Pingpp.apiKey = pingxxProperties.getApiKey();
    Map<String, Object> chargeParams = new HashMap<>();
    chargeParams.put("limit", pingxxProperties.getLimit());
    chargeParams.put("app[id]", pingxxProperties.getAppId());
    ChargeCollection chargeList;
    try {
      chargeList = Charge.list(chargeParams);
    } catch (PingppException e) {
      throw new BizException(BizError.PING_SERVER_ERROR, e.getMessage());
    }
    for (Charge item : chargeList.getData()) {
      if (item.getOrderNo().startsWith(request.getOrderId())) {
        String firstMatchChargeId = item.getId();
        return ChargeIdResponse.builder().chargeId(firstMatchChargeId)
            .build();
      }
    }
    throw new BizException(HttpStatus.NOT_FOUND, BizError.CHARGE_ID_NOT_FOUND_BY_ORDER_ID,
        request.getOrderId());
  }
}