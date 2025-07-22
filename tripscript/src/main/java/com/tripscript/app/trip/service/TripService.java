package com.tripscript.app.trip.service;

import com.tripscript.app.trip.model.Trip;
import com.tripscript.app.trip.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;

    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    public Trip getTrip(Long tripId) {
        return tripRepository.findById(tripId).orElse(null);
    }
}
