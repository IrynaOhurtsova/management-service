package org.bus.ticket.management.controller;

import lombok.RequiredArgsConstructor;
import org.bus.ticket.management.entity.Journey;
import org.bus.ticket.management.service.JourneyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("journey")
public class JourneyController {

    private final JourneyService journeyService;

    @GetMapping
    public Iterable<Journey> getAll() {
        return journeyService.findAll();
    }
}
