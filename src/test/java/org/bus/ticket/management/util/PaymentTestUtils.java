package org.bus.ticket.management.util;

import lombok.experimental.UtilityClass;
import org.bus.ticket.management.dto.BuyTicketResultDto;
import org.bus.ticket.management.dto.PayTicketDto;
import org.bus.ticket.management.dto.PayTicketResultDto;
import org.bus.ticket.management.dto.PaymentStatus;
import org.bus.ticket.management.entity.Payment;

import static org.bus.ticket.management.util.JourneyTestUtils.JOURNEY;
import static org.bus.ticket.management.util.TicketTestUtils.BUY_TICKET_DTO;
import static org.bus.ticket.management.util.TicketTestUtils.TICKET;

@UtilityClass
public class PaymentTestUtils {

    public static final Payment PAYMENT_WITH_FAILED_STATUS = Payment.builder()
            .id(1L)
            .firstName(TICKET.getFirstName())
            .lastName(TICKET.getLastName())
            .patronymic(TICKET.getPatronymic())
            .sum(JOURNEY.getPrice())
            .paymentStatus(PaymentStatus.FAILED).build();
    public static final Payment PAYMENT_WITH_NEW_STATUS = PAYMENT_WITH_FAILED_STATUS.toBuilder()
            .paymentStatus(PaymentStatus.NEW).build();
    public static final Payment PAYMENT_WITH_DONE_STATUS = PAYMENT_WITH_FAILED_STATUS.toBuilder()
            .paymentStatus(PaymentStatus.DONE).build();
    public static final PayTicketDto PAY_TICKET_DTO = PayTicketDto.builder()
            .firstName(BUY_TICKET_DTO.getFirstName())
            .lastName(BUY_TICKET_DTO.getLastName())
            .patronymic(BUY_TICKET_DTO.getPatronymic())
            .sum(JOURNEY.getPrice()).build();
    public static final PayTicketResultDto PAY_TICKET_RESULT_DTO = new PayTicketResultDto(PAYMENT_WITH_NEW_STATUS.getId());

}
