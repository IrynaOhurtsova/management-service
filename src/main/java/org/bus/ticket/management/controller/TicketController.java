package org.bus.ticket.management.controller;

import lombok.RequiredArgsConstructor;
import org.bus.ticket.management.dto.BuyTicketDto;
import org.bus.ticket.management.dto.BuyTicketResultDto;
import org.bus.ticket.management.dto.TicketInformationDto;
import org.bus.ticket.management.service.TicketService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public BuyTicketResultDto buyTicket(@RequestBody BuyTicketDto buyTicketDto) {
        return new BuyTicketResultDto(ticketService.buyTicket(buyTicketDto).getId());
    }

    @GetMapping("{ticketId}")
    public TicketInformationDto getTicketInformation(@PathVariable("ticketId") long ticketId) {
        return ticketService.getTicketInformation(ticketId);
    }
}
