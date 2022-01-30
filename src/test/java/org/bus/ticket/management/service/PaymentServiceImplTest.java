package org.bus.ticket.management.service;

import org.bus.ticket.management.dto.BuyTicketResultDto;
import org.bus.ticket.management.dto.PayTicketResultDto;
import org.bus.ticket.management.dto.PaymentServiceSettings;
import org.bus.ticket.management.dto.PaymentStatus;
import org.bus.ticket.management.entity.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.bus.ticket.management.dto.PaymentStatus.NEW;
import static org.bus.ticket.management.util.PaymentTestUtils.PAY_TICKET_RESULT_DTO;
import static org.bus.ticket.management.util.PaymentTestUtils.PAYMENT_WITH_NEW_STATUS;
import static org.bus.ticket.management.util.TicketTestUtils.PAY_TICKET_DTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.OK;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    private PaymentServiceSettings paymentServiceSettings;
    private PaymentServiceImpl paymentService;

    @BeforeEach
    void setUp() {
        paymentServiceSettings = new PaymentServiceSettings();
        paymentServiceSettings.setBaseUrl("baseUrl");

        paymentService = new PaymentServiceImpl(restTemplate, paymentServiceSettings);
    }

    @Test
    void buyTicket() {
        ResponseEntity<PayTicketResultDto> buyTicketResultDtoResponseEntity = new ResponseEntity<>(PAY_TICKET_RESULT_DTO, OK);

        when(restTemplate.postForEntity(paymentServiceSettings.getBaseUrl(), PAY_TICKET_DTO, PayTicketResultDto.class)).thenReturn(buyTicketResultDtoResponseEntity);

        assertEquals(PAY_TICKET_RESULT_DTO.getPaymentId(), paymentService.buyTicket(PAY_TICKET_DTO));
    }

    @Test
    void getStatusById() {
        ResponseEntity<PaymentStatus> paymentStatusResponseEntity = new ResponseEntity<>(PAYMENT_WITH_NEW_STATUS.getPaymentStatus(), OK);

        when(restTemplate.getForEntity(paymentServiceSettings.getBaseUrl() + "/" + PAYMENT_WITH_NEW_STATUS.getId() + "/status", PaymentStatus.class)).thenReturn(paymentStatusResponseEntity);

        assertEquals(NEW, paymentService.getStatusById(PAYMENT_WITH_NEW_STATUS.getId()));
    }

    @Test
    void findAllByPaymentStatus() {
        List<Payment> payments = Collections.singletonList(PAYMENT_WITH_NEW_STATUS);
        ResponseEntity<Payment[]> responseEntity = new ResponseEntity<>(payments.toArray(new Payment[0]), OK);

        when(restTemplate.getForEntity(paymentServiceSettings.getBaseUrl() + "/status/" + NEW, Payment[].class)).thenReturn(responseEntity);

        assertEquals(payments, paymentService.findAllByPaymentStatus(NEW));
    }

    @Test
    void saveAll() {
        paymentService.saveAll(Collections.singletonList(PAYMENT_WITH_NEW_STATUS));

        verify(restTemplate, times(1)).put(paymentServiceSettings.getBaseUrl(), Collections.singletonList(PAYMENT_WITH_NEW_STATUS));
    }
}