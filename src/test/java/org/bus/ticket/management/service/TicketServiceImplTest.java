package org.bus.ticket.management.service;

import org.bus.ticket.management.mapper.ToTicketInformationDtoMapper;
import org.bus.ticket.management.repository.JourneyRepository;
import org.bus.ticket.management.repository.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static java.lang.String.format;
import static org.bus.ticket.management.dto.PaymentStatus.NEW;
import static org.bus.ticket.management.util.JourneyTestUtils.*;
import static org.bus.ticket.management.util.PaymentTestUtils.PAYMENT_WITH_NEW_STATUS;
import static org.bus.ticket.management.util.TicketTestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketServiceImplTest {

    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private JourneyRepository journeyRepository;
    @Mock
    private PaymentService paymentService;
    @Mock
    private ToTicketInformationDtoMapper toTicketInformationDtoMapper;
    @InjectMocks
    private TicketServiceImpl ticketService;

    @Test
    void buyTicket() {
        when(journeyRepository.findByIdForUpdate(BUY_TICKET_DTO.getJourneyId())).thenReturn(Optional.of(JOURNEY));
        when(paymentService.buyTicket(PAY_TICKET_DTO)).thenReturn(1L);
        when(journeyRepository.save(JOURNEY_WITH_BUY_TICKET)).thenReturn(JOURNEY_WITH_BUY_TICKET);
        when(ticketRepository.save(TICKET)).thenReturn(SAVED_TICKET);

        assertEquals(SAVED_TICKET, ticketService.buyTicket(BUY_TICKET_DTO));
    }

    @Test
    void buyTicketWithWrongJourneyId() {
        when(journeyRepository.findByIdForUpdate(BUY_TICKET_DTO.getJourneyId())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> ticketService.buyTicket(BUY_TICKET_DTO), format("journey with id %s not found", BUY_TICKET_DTO.getJourneyId()));
    }

    @Test
    void buyTicketWithZeroFreePlaces() {
        when(journeyRepository.findByIdForUpdate(BUY_TICKET_DTO.getJourneyId())).thenReturn(Optional.of(JOURNEY_WITHOUT_FREE_PLACES));

        assertThrows(IllegalStateException.class, () -> ticketService.buyTicket(BUY_TICKET_DTO), "journey does not have free places");
    }

    @Test
    void getTicketInformation() {
        when(ticketRepository.findById(SAVED_TICKET.getId())).thenReturn(Optional.of(SAVED_TICKET));
        when(journeyRepository.findById(SAVED_TICKET.getJourneyId())).thenReturn(Optional.of(JOURNEY));
        when(paymentService.findById(TICKET.getPaymentId())).thenReturn(PAYMENT_WITH_NEW_STATUS);
        when(toTicketInformationDtoMapper.mapToTicketInformationDto(SAVED_TICKET, JOURNEY, NEW)).thenReturn(TICKET_INFORMATION_DTO);

        assertEquals(TICKET_INFORMATION_DTO, ticketService.getTicketInformation(SAVED_TICKET.getId()));

    }

    @Test
    void getTicketInformationWithWrongTicketId() {
        when(ticketRepository.findById(SAVED_TICKET.getId())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> ticketService.getTicketInformation(SAVED_TICKET.getId()), format("ticket with id %s not found", SAVED_TICKET.getId()));
    }

    @Test
    void findTicketByPaymentId() {
        when(ticketRepository.findByPaymentId(SAVED_TICKET.getPaymentId())).thenReturn(SAVED_TICKET);

        assertEquals(SAVED_TICKET, ticketService.findTicketByPaymentId(SAVED_TICKET.getPaymentId()));
    }
}