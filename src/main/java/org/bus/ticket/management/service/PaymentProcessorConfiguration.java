package org.bus.ticket.management.service;

import org.bus.ticket.management.dto.PaymentStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

@Configuration
public class PaymentProcessorConfiguration {

    private static final PaymentProcessor STUB = payment -> payment;

    @Bean
    Map<PaymentStatus, PaymentProcessor> paymentProcessorByStatus(
            FailedStatusPaymentProcessor failedStatusPaymentProcessor) {
        EnumMap<PaymentStatus, PaymentProcessor> paymentProcessors = new EnumMap<>(PaymentStatus.class);
        paymentProcessors.put(PaymentStatus.NEW, STUB);
        paymentProcessors.put(PaymentStatus.DONE, STUB);
        paymentProcessors.put(PaymentStatus.FAILED, failedStatusPaymentProcessor);
        return Collections.unmodifiableMap(paymentProcessors);
    }
}
