package org.bus.ticket.management.dto;

import lombok.Value;

@Value
public class BuyTicketDto {

    String firstName;
    String lastName;
    String patronymic;
    long journeyId;
}
