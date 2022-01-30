package org.bus.ticket.management.service;

import org.bus.ticket.management.repository.JourneyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.bus.ticket.management.util.JourneyTestUtils.JOURNEY_WITH_BUY_TICKET;
import static org.bus.ticket.management.util.JourneyTestUtils.JOURNEY_WITH_RETURN_TICKET;
import static org.bus.ticket.management.util.PaymentTestUtils.PAYMENT_WITH_FAILED_STATUS;
import static org.bus.ticket.management.util.TicketTestUtils.SAVED_TICKET;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FailedStatusPaymentProcessorTest {

    @Mock
    private TicketService ticketService;
    @Mock
    private JourneyRepository journeyRepository;
    @InjectMocks
    private FailedStatusPaymentProcessor failedStatusPaymentProcessor;

    @Test
    void process() {
        when(ticketService.findTicketByPaymentId(PAYMENT_WITH_FAILED_STATUS.getId())).thenReturn(SAVED_TICKET);
        when(journeyRepository.findByIdForUpdate(SAVED_TICKET.getJourneyId())).thenReturn(Optional.of(JOURNEY_WITH_BUY_TICKET));
        when(journeyRepository.save(JOURNEY_WITH_RETURN_TICKET)).thenReturn(JOURNEY_WITH_RETURN_TICKET);

        assertEquals(PAYMENT_WITH_FAILED_STATUS, failedStatusPaymentProcessor.process(PAYMENT_WITH_FAILED_STATUS));
    }
}