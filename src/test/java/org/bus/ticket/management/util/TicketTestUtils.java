package org.bus.ticket.management.util;

import lombok.experimental.UtilityClass;
import org.bus.ticket.management.dto.BuyTicketDto;
import org.bus.ticket.management.dto.PayTicketDto;
import org.bus.ticket.management.dto.TicketInformationDto;
import org.bus.ticket.management.entity.Journey;
import org.bus.ticket.management.entity.Ticket;

import java.time.LocalTime;

import static org.bus.ticket.management.dto.PaymentStatus.NEW;

@UtilityClass
public class TicketTestUtils {

    public static final BuyTicketDto BUY_TICKET_DTO = new BuyTicketDto("Ivan", "Ivanov", "Ivanovich", 1L);
    public static final Journey JOURNEY = Journey.builder()
            .id(1L)
            .departureStation("Dnipro")
            .destinationStation("Kyiv")
            .time(LocalTime.of(14, 0))
            .price(500)
            .freePlaces(18).build();
    public static final Journey JOURNEY_WITHOUT_FREE_PLACES = Journey.builder()
            .id(1L)
            .departureStation("Dnipro")
            .destinationStation("Kyiv")
            .time(LocalTime.of(14, 0))
            .price(500)
            .freePlaces(0).build();
    public static final Journey JOURNEY_WITH_BUY_TICKET = JOURNEY.decreaseFreePlaces();
    public static final PayTicketDto PAY_TICKET_DTO = PayTicketDto.builder()
            .firstName(BUY_TICKET_DTO.getFirstName())
            .lastName(BUY_TICKET_DTO.getLastName())
            .patronymic(BUY_TICKET_DTO.getPatronymic())
            .sum(JOURNEY.getPrice()).build();
    public static final Ticket TICKET = Ticket.builder()
            .firstName(BUY_TICKET_DTO.getFirstName())
            .lastName(BUY_TICKET_DTO.getLastName())
            .patronymic(BUY_TICKET_DTO.getPatronymic())
            .journeyId(JOURNEY.getId())
            .paymentId(1L).build();
    public static final Ticket SAVED_TICKET = Ticket.builder()
            .id(1L)
            .firstName(BUY_TICKET_DTO.getFirstName())
            .lastName(BUY_TICKET_DTO.getLastName())
            .patronymic(BUY_TICKET_DTO.getPatronymic())
            .journeyId(JOURNEY.getId())
            .paymentId(1L).build();
    public static final TicketInformationDto TICKET_INFORMATION_DTO = TicketInformationDto.builder()
            .id(SAVED_TICKET.getId())
            .departureStation(JOURNEY.getDepartureStation())
            .destinationStation(JOURNEY.getDestinationStation())
            .freePlaces(JOURNEY.getFreePlaces())
            .price(JOURNEY.getPrice())
            .time(JOURNEY.getTime())
            .paymentStatus(NEW).build();
}
