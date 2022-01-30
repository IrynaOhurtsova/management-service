package org.bus.ticket.management.service;

import org.bus.ticket.management.entity.Journey;

public interface JourneyService {

    Iterable<Journey> findAll();
}
