package org.bus.ticket.management.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalTime;

@Data
@Builder(toBuilder = true)
public class Journey {

    @Id
    long id;
    String departureStation;
    String destinationStation;
    LocalTime time;
    double price;
    int freePlaces;

    public Journey decreaseFreePlaces() {
        return this.toBuilder()
                .freePlaces(freePlaces - 1)
                .build();
    }

    public Journey increaseFreePlaces() {
        return this.toBuilder()
                .freePlaces(freePlaces + 1)
                .build();
    }
}
