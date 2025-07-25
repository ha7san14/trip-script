package com.tripscript.app.trip.service;

import com.tripscript.app.city.model.City;
import com.tripscript.app.city.service.CityService;
import com.tripscript.app.trip.dto.TripRequestDto;
import com.tripscript.app.trip.model.Trip;
import com.tripscript.app.trip.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final CityService cityService;

    public List<Trip> getAllTrips() {
        return tripRepository.findAll();
    }

    public Trip getTrip(Long tripId) {
        return tripRepository.findById(tripId).orElse(null);
    }

    public void deleteTrip(Long tripId) {
        tripRepository.deleteById(tripId);
    }

    public Trip createTrip(TripRequestDto tripRequestDto) {
        City city = cityService.getCity(tripRequestDto.getCityId());
        Trip trip = new Trip();
        trip.setCity(city);
        trip.setStartDate(tripRequestDto.getStartDate());
        trip.setEndDate(tripRequestDto.getEndDate());
        trip.setRating(tripRequestDto.getRating());
        trip.setNotes(tripRequestDto.getNotes());
        return tripRepository.save(trip);
    }

    public Trip updateTrip(Long tripId, Trip trip) {
        Trip existingTrip = tripRepository.findById(tripId).orElseThrow(() -> new
                RuntimeException("City not found with id " + tripId));
        existingTrip.setCity(trip.getCity());
        existingTrip.setNotes(trip.getNotes());
        existingTrip.setRating(trip.getRating());
        existingTrip.setStartDate(trip.getStartDate());
        existingTrip.setEndDate(trip.getEndDate());

        return tripRepository.save(existingTrip);
    }

}
