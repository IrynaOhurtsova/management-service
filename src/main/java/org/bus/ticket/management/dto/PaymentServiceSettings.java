package org.bus.ticket.management.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("payment.service")
public class PaymentServiceSettings {

    private String baseUrl;
}
