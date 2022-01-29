package org.bus.ticket.management.entity;

import lombok.Builder;
import lombok.Value;
import org.bus.ticket.management.dto.PaymentStatus;

@Value
@Builder(toBuilder = true)
public class Payment {

    long id;
    String lastName;
    String firstName;
    String patronymic;
    double sum;
    PaymentStatus paymentStatus;
}
