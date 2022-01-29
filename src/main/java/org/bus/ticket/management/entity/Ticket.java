package org.bus.ticket.management.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
public class Ticket {

    @Id
    long id;
    String lastName;
    String firstName;
    String patronymic;
    long journeyId;
    long paymentId;
}
