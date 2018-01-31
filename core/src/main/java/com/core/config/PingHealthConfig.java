package com.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ankush.a on 04/01/17.
 */
@Component
@ConfigurationProperties(prefix = "pingHealthConfig")
@Data
public class PingHealthConfig {

    public Map<String, Long> tenantsTiming = new HashMap<String, Long>();

}
