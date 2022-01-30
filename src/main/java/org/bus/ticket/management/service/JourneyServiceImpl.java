package org.bus.ticket.management.service;

import lombok.RequiredArgsConstructor;
import org.bus.ticket.management.entity.Journey;
import org.bus.ticket.management.repository.JourneyRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JourneyServiceImpl implements JourneyService {

    private final JourneyRepository journeyRepository;

    @Override
    public Iterable<Journey> findAll() {
        return journeyRepository.findAll();
    }
}
