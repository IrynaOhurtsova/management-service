package org.bus.ticket.management.repository;

import org.bus.ticket.management.entity.Journey;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JourneyRepository extends CrudRepository<Journey, Long> {

    @Query("SELECT * FROM journey WHERE id = :id FOR UPDATE")
    Optional<Journey> findByIdForUpdate(@Param("id") long journeyId);

}
