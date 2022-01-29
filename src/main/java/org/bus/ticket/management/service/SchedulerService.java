package org.bus.ticket.management.service;

import lombok.RequiredArgsConstructor;
import org.bus.ticket.management.dto.PaymentStatus;
import org.bus.ticket.management.entity.Payment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static org.bus.ticket.management.dto.PaymentStatus.NEW;

@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final PaymentService paymentService;
    private final Map<PaymentStatus, PaymentProcessor> paymentProcessorByStatus;

    @Scheduled(fixedDelayString = "3000")
    @Transactional
    public void checkPaymentStatus() {
        List<Payment> updatedPayments = paymentService.findAllByPaymentStatus(NEW)
                .stream()
                .map(this::processNewPayment)
                .collect(toList());
        paymentService.saveAll(updatedPayments);
    }

    private Payment processNewPayment(Payment payment) {
        PaymentStatus updatedPaymentStatus = paymentService.getStatusById(payment.getId());
        PaymentProcessor paymentProcessor = paymentProcessorByStatus.get(updatedPaymentStatus);
        return paymentProcessor.process(payment.toBuilder()
                .paymentStatus(updatedPaymentStatus)
                .build());
    }
}
