package org.bus.ticket.management.service;

import org.bus.ticket.management.dto.PayTicketDto;
import org.bus.ticket.management.dto.PaymentStatus;
import org.bus.ticket.management.entity.Payment;

import java.util.List;

public interface PaymentService {


    long buyTicket(PayTicketDto payTicketDto);

    PaymentStatus getStatusById(long paymentId);

    List<Payment> findAllByPaymentStatus(PaymentStatus paymentStatus);

    void saveAll(List<Payment> payments);
}
