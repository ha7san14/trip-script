package com.tripscript.app.trip.controller;

import com.tripscript.app.constants.Endpoints;
import com.tripscript.app.trip.dto.TripRequestDto;
import com.tripscript.app.trip.model.Trip;
import com.tripscript.app.trip.service.TripService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @GetMapping(Endpoints.GET_ALL_TRIPS)
    public ResponseEntity<List<Trip>> getAllTrips() {
        List<Trip> trips = tripService.getAllTrips();
        if (trips.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(trips);
    }

    @GetMapping(Endpoints.GET_TRIP)
    public ResponseEntity<Trip> getTrip(@PathVariable Long tripId) {
        Trip getTrip = tripService.getTrip(tripId);
        if (getTrip != null) {
            return ResponseEntity.ok(getTrip);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(Endpoints.DELETE_TRIP)
    public ResponseEntity<Void> deleteTrip(@PathVariable Long tripId) {
        Trip getTrip = tripService.getTrip(tripId);
        if (getTrip != null) {
            tripService.deleteTrip(tripId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(Endpoints.CREATE_TRIP)
    public ResponseEntity<?> createTrip(@Valid @RequestBody TripRequestDto tripRequestDto) {
        try {
            Trip newTrip = tripService.createTrip(tripRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(newTrip);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @PutMapping(Endpoints.MODIFY_TRIP)
    public ResponseEntity<Trip> modifyTrip(@PathVariable Long tripId, @Valid @RequestBody Trip trip) {
        Trip existingTrip = tripService.updateTrip(tripId, trip);
        return ResponseEntity.ok(existingTrip);
    }
}
