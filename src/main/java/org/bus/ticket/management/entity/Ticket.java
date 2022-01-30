package org.bus.ticket.management.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
public class Ticket {

    @Id
    private long id;
    private String lastName;
    private String firstName;
    private String patronymic;
    private long journeyId;
    private long paymentId;
}
