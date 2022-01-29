package org.bus.ticket.management.repository;

import org.bus.ticket.management.entity.Ticket;
import org.springframework.data.repository.CrudRepository;

public interface TicketRepository extends CrudRepository<Ticket, Long> {

    Ticket findByPaymentId(long paymentId);
}
