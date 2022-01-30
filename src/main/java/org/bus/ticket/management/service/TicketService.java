package org.bus.ticket.management.service;

import org.bus.ticket.management.dto.BuyTicketDto;
import org.bus.ticket.management.dto.TicketInformationDto;
import org.bus.ticket.management.entity.Ticket;

public interface TicketService {

    Ticket buyTicket(BuyTicketDto buyTicketDto);

    TicketInformationDto getTicketInformation(long ticketId);

    Ticket findTicketByPaymentId(long paymentId);
}
