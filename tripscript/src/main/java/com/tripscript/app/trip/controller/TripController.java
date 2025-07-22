package com.tripscript.app.trip.controller;

import com.tripscript.app.constants.Endpoints;
import com.tripscript.app.trip.model.Trip;
import com.tripscript.app.trip.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @GetMapping(Endpoints.GET_ALL_TRIPS)
    public ResponseEntity<List<Trip>> getAllTrips() {
        List<Trip> trips = tripService.getAllTrips();
        if(trips.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(trips);
    }

    @GetMapping(Endpoints.GET_TRIP)
    public ResponseEntity<Trip> getTrip(@PathVariable Long tripId) {
        Trip trip = tripService.getTrip(tripId);
        if(trip != null) {
            return ResponseEntity.ok(trip);
        }
        return ResponseEntity.notFound().build();
    }
}
