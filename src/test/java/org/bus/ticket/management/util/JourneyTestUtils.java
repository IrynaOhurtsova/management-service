package org.bus.ticket.management.util;

import lombok.experimental.UtilityClass;
import org.bus.ticket.management.entity.Journey;

import java.time.LocalTime;

@UtilityClass
public class JourneyTestUtils {

    public static final Journey JOURNEY = Journey.builder()
            .id(1L)
            .departureStation("Dnipro")
            .destinationStation("Kyiv")
            .time(LocalTime.of(14, 0))
            .price(500)
            .freePlaces(18).build();
    public static final Journey JOURNEY_WITHOUT_FREE_PLACES = Journey.builder()
            .id(1L)
            .departureStation("Dnipro")
            .destinationStation("Kyiv")
            .time(LocalTime.of(14, 0))
            .price(500)
            .freePlaces(0).build();
    public static final Journey JOURNEY_WITH_BUY_TICKET = JOURNEY.decreaseFreePlaces();
    public static final Journey JOURNEY_WITH_RETURN_TICKET = JOURNEY_WITH_BUY_TICKET.increaseFreePlaces();
}
