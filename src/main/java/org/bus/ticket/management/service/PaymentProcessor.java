package org.bus.ticket.management.service;

import org.bus.ticket.management.entity.Payment;

public interface PaymentProcessor {

    Payment process(Payment payment);
}
