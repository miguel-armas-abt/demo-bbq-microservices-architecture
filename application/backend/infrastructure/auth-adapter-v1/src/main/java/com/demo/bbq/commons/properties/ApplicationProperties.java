package com.demo.bbq.commons.properties;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "configuration")
public class ApplicationProperties extends ConfigurationBaseProperties {

  public String searchEndpoint(String serviceName) {
    return this.getRestClients().get(serviceName).getRequest().getEndpoint();
  }

  public Map<String, String> searchVariables(String serviceName) {
    return this.getRestClients().get(serviceName).getVariables();
  }

}
