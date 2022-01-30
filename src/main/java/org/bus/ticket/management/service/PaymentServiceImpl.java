package org.bus.ticket.management.service;

import lombok.RequiredArgsConstructor;
import org.bus.ticket.management.dto.PayTicketDto;
import org.bus.ticket.management.dto.PayTicketResultDto;
import org.bus.ticket.management.dto.PaymentServiceSettings;
import org.bus.ticket.management.dto.PaymentStatus;
import org.bus.ticket.management.entity.Payment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final RestTemplate restTemplate;
    private final PaymentServiceSettings paymentServiceSettings;

    @Override
    public long buyTicket(PayTicketDto payTicketDto) {
        ResponseEntity<PayTicketResultDto> buyTicketResultDtoResponseEntity = restTemplate.postForEntity(paymentServiceSettings.getBaseUrl(), payTicketDto, PayTicketResultDto.class);
        return buyTicketResultDtoResponseEntity.getBody().getPaymentId();
    }

    @Override
    public PaymentStatus getStatusById(long paymentId) {
        ResponseEntity<PaymentStatus> paymentStatusResponseEntity = restTemplate.getForEntity(paymentServiceSettings.getBaseUrl() + "/" + paymentId + "/status", PaymentStatus.class);
        return paymentStatusResponseEntity.getBody();
    }

    @Override
    public List<Payment> findAllByPaymentStatus(PaymentStatus paymentStatus) {
        ResponseEntity<Payment[]> paymentResponseEntity = restTemplate.getForEntity(paymentServiceSettings.getBaseUrl() + "/status/" + paymentStatus, Payment[].class);
        return Arrays.asList(paymentResponseEntity.getBody());
    }

    @Override
    public void saveAll(List<Payment> payments) {
        restTemplate.put(paymentServiceSettings.getBaseUrl(), payments);
    }

    @Override
    public Payment findById(long paymentId) {
        ResponseEntity<Payment> paymentResponseEntity = restTemplate.getForEntity(paymentServiceSettings.getBaseUrl() + "/" + paymentId, Payment.class);
        return paymentResponseEntity.getBody();
    }
}
