package com.ping.utils.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class PingxxProperties {

  @Value("${pingApp.apiKey}")
  private String apiKey;
  @Value("${pingApp.appId}")
  private String appId;
  @Value("${pingApp.limit:100}")
  private Integer limit;
  @Value("${pingApp.privateKeyFilePath}")
  private String privateKeyFilePath;
}
