package com.tripscript.app.trip.service;

import com.tripscript.app.city.model.City;
import com.tripscript.app.city.service.CityService;
import com.tripscript.app.trip.dto.TripRequestDto;
import com.tripscript.app.trip.model.Trip;
import com.tripscript.app.trip.repository.TripRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TripServiceTest {

    @Mock
    private TripRepository tripRepository;

    @Mock
    private CityService cityService;

    @InjectMocks
    private TripService tripService;

    @Test
    void shouldReturnAllTrips() {
        when(tripRepository.findAll()).thenReturn(List.of(new Trip(), new Trip()));

        List<Trip> trips = tripService.getAllTrips();

        assertEquals(2, trips.size());
        verify(tripRepository).findAll();
    }

    @Test
    void shouldReturnTripWhenExists() {
        Trip trip = new Trip();
        trip.setTripId(1L);

        when(tripRepository.findById(1L)).thenReturn(Optional.of(trip));

        Trip result = tripService.getTrip(1L);

        assertEquals(1L, result.getTripId());
    }

    @Test
    void shouldThrowWhenTripNotFound() {
        when(tripRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                tripService.getTrip(1L));

        assertEquals("Trip with ID 1 not found.", exception.getMessage());
    }

    @Test
    void shouldDeleteTripWhenExists() {
        when(tripRepository.existsById(1L)).thenReturn(true);

        tripService.deleteTrip(1L);

        verify(tripRepository).deleteById(1L);
    }

    @Test
    void shouldThrowWhenDeletingNonExistingTrip() {
        when(tripRepository.existsById(1L)).thenReturn(false);

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () ->
                tripService.deleteTrip(1L));

        assertEquals("Trip with ID 1 not found.", ex.getMessage());
    }

    @Test
    void shouldCreateTripSuccessfully() {
        TripRequestDto dto = new TripRequestDto();
        dto.setCityId(10L);
        dto.setStartDate(LocalDate.of(2025, 1, 1));
        dto.setEndDate(LocalDate.of(2025, 1, 5));
        dto.setRating(5);
        dto.setNotes("Fun trip!");

        City city = new City();
        city.setCityId(10L);

        when(cityService.getCity(10L)).thenReturn(city);
        when(tripRepository.save(any(Trip.class))).thenAnswer(i -> i.getArgument(0));

        Trip result = tripService.createTrip(dto);

        assertEquals(5, result.getRating());
        assertEquals("Fun trip!", result.getNotes());
        assertEquals(city, result.getCity());
    }

    @Test
    void shouldUpdateTripSuccessfully() {
        Trip existingTrip = new Trip();
        existingTrip.setTripId(1L);

        TripRequestDto dto = new TripRequestDto();
        dto.setCityId(10L);
        dto.setStartDate(LocalDate.of(2025, 1, 2));
        dto.setEndDate(LocalDate.of(2025, 1, 10));
        dto.setRating(4);
        dto.setNotes("Updated");

        City city = new City();
        city.setCityId(10L);

        when(tripRepository.findById(1L)).thenReturn(Optional.of(existingTrip));
        when(cityService.getCity(10L)).thenReturn(city);
        when(tripRepository.save(any(Trip.class))).thenAnswer(i -> i.getArgument(0));

        Trip result = tripService.updateTrip(1L, dto);

        assertEquals(4, result.getRating());
        assertEquals("Updated", result.getNotes());
        assertEquals(city, result.getCity());
    }

    @Test
    void shouldThrowWhenUpdatingNonExistingTrip() {
        TripRequestDto dto = new TripRequestDto();

        when(tripRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () ->
                tripService.updateTrip(1L, dto));

        assertEquals("Trip with ID 1 not found.", ex.getMessage());
    }

}
