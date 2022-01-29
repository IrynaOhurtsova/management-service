package org.bus.ticket.management.service;

import lombok.RequiredArgsConstructor;
import org.bus.ticket.management.entity.Journey;
import org.bus.ticket.management.entity.Payment;
import org.bus.ticket.management.entity.Ticket;
import org.bus.ticket.management.repository.JourneyRepository;
import org.springframework.data.annotation.Version;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FailedStatusPaymentProcessor implements PaymentProcessor {

    private final TicketService ticketService;
    private final JourneyRepository journeyRepository;

    @Override
    public Payment process(Payment payment) {
        Ticket ticket = ticketService.findTicketByPaymentId(payment.getId());
        Journey journey = journeyRepository.findByIdForUpdate(ticket.getJourneyId())
                .orElseThrow(IllegalStateException::new)
                .increaseFreePlaces();
        journeyRepository.save(journey);
        return payment;
    }
}
