package org.bus.ticket.management.controller;

import lombok.RequiredArgsConstructor;
import org.bus.ticket.management.dto.BuyTicketDto;
import org.bus.ticket.management.dto.BuyTicketResultDto;
import org.bus.ticket.management.service.TicketService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/buy")
    public BuyTicketResultDto buyTicket(@RequestBody BuyTicketDto buyTicketDto) {
        return new BuyTicketResultDto(ticketService.buyTicket(buyTicketDto).getId());
    }
}
