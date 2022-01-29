package org.bus.ticket.management.service;

import lombok.RequiredArgsConstructor;
import org.bus.ticket.management.dto.BuyTicketDto;
import org.bus.ticket.management.dto.PayTicketDto;
import org.bus.ticket.management.dto.PaymentStatus;
import org.bus.ticket.management.dto.TicketInformationDto;
import org.bus.ticket.management.entity.Journey;
import org.bus.ticket.management.entity.Ticket;
import org.bus.ticket.management.mapper.ToTicketInformationDtoMapper;
import org.bus.ticket.management.repository.JourneyRepository;
import org.bus.ticket.management.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final JourneyRepository journeyRepository;
    private final PaymentService paymentService;
    private final ToTicketInformationDtoMapper toTicketInformationDtoMapper;

    @Override
    @Transactional
    public Ticket buyTicket(BuyTicketDto buyTicketDto) {
        Journey journey = journeyRepository.findByIdForUpdate(buyTicketDto.getJourneyId())
                .orElseThrow(() -> new IllegalArgumentException(format("journey with id %s not found", buyTicketDto.getJourneyId())))
                .decreaseFreePlaces();
        if (journey.getFreePlaces() < 0) {
            throw new IllegalStateException("journey does not have free places");
        }
        long paymentId = paymentService.buyTicket(PayTicketDto.builder()
                .firstName(buyTicketDto.getFirstName())
                .lastName(buyTicketDto.getLastName())
                .patronymic(buyTicketDto.getPatronymic())
                .sum(journey.getPrice())
                .build());
        Ticket ticket = Ticket.builder()
                .firstName(buyTicketDto.getFirstName())
                .lastName(buyTicketDto.getLastName())
                .patronymic(buyTicketDto.getPatronymic())
                .journeyId(journey.getId())
                .paymentId(paymentId).
                build();
        journeyRepository.save(journey);
        return ticketRepository.save(ticket);
    }

    @Override
    public TicketInformationDto getTicketInformation(long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException(format("ticket with id %s not found", ticketId)));
        Journey journey = journeyRepository.findById(ticket.getJourneyId())
                .orElseThrow(() -> new IllegalArgumentException(format("journey with id %s not found", ticket.getJourneyId())));
        PaymentStatus paymentStatus = paymentService.getStatusById(ticket.getPaymentId());
        return toTicketInformationDtoMapper.mapToTicketInformationDto(ticket, journey, paymentStatus);
    }

    @Override
    public Ticket findTicketByPaymentId(long paymentId) {
        return ticketRepository.findByPaymentId(paymentId);
    }
}
