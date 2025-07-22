package com.tripscript.app.trip.repository;

import com.tripscript.app.trip.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip,Long> {
}
