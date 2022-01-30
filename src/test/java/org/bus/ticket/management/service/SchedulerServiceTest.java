package org.bus.ticket.management.service;

import org.bus.ticket.management.dto.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.EnumMap;

import static org.bus.ticket.management.dto.PaymentStatus.*;
import static org.bus.ticket.management.util.PaymentTestUtils.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SchedulerServiceTest {

    @Mock
    private PaymentService paymentService;
    @Mock
    private FailedStatusPaymentProcessor failedStatusPaymentProcessor;
    @Mock
    private PaymentProcessor newAndDoneStatusPaymentProcessor;

    private SchedulerService schedulerService;

    @BeforeEach
    void setUp() {
        EnumMap<PaymentStatus, PaymentProcessor> paymentProcessors = new EnumMap<>(PaymentStatus.class);
        paymentProcessors.put(NEW, newAndDoneStatusPaymentProcessor);
        paymentProcessors.put(DONE, newAndDoneStatusPaymentProcessor);
        paymentProcessors.put(PaymentStatus.FAILED, failedStatusPaymentProcessor);

        schedulerService = new SchedulerService(paymentService, Collections.unmodifiableMap(paymentProcessors));
    }

    @Test
    void checkPaymentStatusNew() {
        when(paymentService.findAllByPaymentStatus(NEW)).thenReturn(Collections.singletonList(PAYMENT_WITH_NEW_STATUS));
        when(paymentService.getStatusById(PAYMENT_WITH_NEW_STATUS.getId())).thenReturn(NEW);
        when(newAndDoneStatusPaymentProcessor.process(PAYMENT_WITH_NEW_STATUS)).thenReturn(PAYMENT_WITH_NEW_STATUS);

        schedulerService.checkPaymentStatus();

        verify(paymentService, times(1)).saveAll(Collections.singletonList(PAYMENT_WITH_NEW_STATUS));
    }

    @Test
    void checkPaymentStatusDone() {
        when(paymentService.findAllByPaymentStatus(NEW)).thenReturn(Collections.singletonList(PAYMENT_WITH_NEW_STATUS));
        when(paymentService.getStatusById(PAYMENT_WITH_NEW_STATUS.getId())).thenReturn(DONE);
        when(newAndDoneStatusPaymentProcessor.process(PAYMENT_WITH_DONE_STATUS)).thenReturn(PAYMENT_WITH_DONE_STATUS);

        schedulerService.checkPaymentStatus();

        verify(paymentService, times(1)).saveAll(Collections.singletonList(PAYMENT_WITH_DONE_STATUS));
    }

    @Test
    void checkPaymentStatusFailed() {
        when(paymentService.findAllByPaymentStatus(NEW)).thenReturn(Collections.singletonList(PAYMENT_WITH_NEW_STATUS));
        when(paymentService.getStatusById(PAYMENT_WITH_NEW_STATUS.getId())).thenReturn(FAILED);
        when(failedStatusPaymentProcessor.process(PAYMENT_WITH_FAILED_STATUS)).thenReturn(PAYMENT_WITH_FAILED_STATUS);

        schedulerService.checkPaymentStatus();

        verify(paymentService, times(1)).saveAll(Collections.singletonList(PAYMENT_WITH_FAILED_STATUS));
    }
}