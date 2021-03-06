package org.bus.ticket.management.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalTime;

@Data
@Builder(toBuilder = true)
public class Journey {

    @Id
    private long id;
    private String departureStation;
    private String destinationStation;
    private LocalTime time;
    private double price;
    private int freePlaces;

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
