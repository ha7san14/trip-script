package com.tripscript.app.trip.service;

import com.tripscript.app.city.model.City;
import com.tripscript.app.city.service.CityService;
import com.tripscript.app.trip.dto.TripRequestDto;
import com.tripscript.app.trip.model.Trip;
import com.tripscript.app.trip.repository.TripRepository;
import jakarta.persistence.EntityNotFoundException;
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
        return tripRepository.findById(tripId).orElseThrow(() ->
                new EntityNotFoundException(("Trip with ID " + tripId + " not found.")));
    }

    public void deleteTrip(Long tripId) {
        if (!tripRepository.existsById(tripId)) {
            throw new EntityNotFoundException("Trip with ID " + tripId + " not found.");
        }
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

    public Trip updateTrip(Long tripId, TripRequestDto tripRequestDto) {
        Trip existingTrip = tripRepository.findById(tripId).orElseThrow(() ->
                new EntityNotFoundException("Trip with ID " + tripId + " not found."));
        City city = cityService.getCity(tripRequestDto.getCityId());
        existingTrip.setCity(city);
        existingTrip.setStartDate(tripRequestDto.getStartDate());
        existingTrip.setEndDate(tripRequestDto.getEndDate());
        existingTrip.setRating(tripRequestDto.getRating());
        existingTrip.setNotes(tripRequestDto.getNotes());

        return tripRepository.save(existingTrip);
    }

}
