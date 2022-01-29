package org.bus.ticket.management.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.time.LocalTime;
import java.util.UUID;

@Value
@Builder
public class TicketInformationDto {

    long id;
    String departureStation;
    String destinationStation;
    LocalTime time;
    double price;
    int freePlaces;
    PaymentStatus paymentStatus;

}
