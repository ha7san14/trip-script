package com.tripscript.app.trip.controller;

import com.tripscript.app.trip.dto.TripRequestDto;
import com.tripscript.app.trip.model.Trip;
import com.tripscript.app.trip.service.TripService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TripControllerTest {

    @Mock
    private TripService tripService;

    @InjectMocks
    private TripController tripController;

    @Test
    void shouldReturnAllTrips() {
        List<Trip> mockedTrips = List.of(new Trip(), new Trip());
        when(tripService.getAllTrips()).thenReturn(mockedTrips);

        ResponseEntity<List<Trip>> mockedResponse = tripController.getAllTrips();

        assertEquals(HttpStatus.OK, mockedResponse.getStatusCode());
        assertEquals(mockedTrips, mockedResponse.getBody());
    }

    @Test
    void shouldReturnNoContentWhenNoTrips() {
        when(tripService.getAllTrips()).thenReturn(List.of());

        ResponseEntity<List<Trip>> mockedResponse = tripController.getAllTrips();

        assertEquals(HttpStatus.NO_CONTENT, mockedResponse.getStatusCode());
    }

    @Test
    void shouldReturnTripWhenExists() {
        Trip trip = new Trip();
        trip.setTripId(1L);
        when(tripService.getTrip(1L)).thenReturn(trip);

        ResponseEntity<?> mockedResponse = tripController.getTrip(1L);

        assertEquals(HttpStatus.OK, mockedResponse.getStatusCode());
        assertEquals(trip, mockedResponse.getBody());
    }

    @Test
    void shouldReturnNotFoundWhenTripDoesNotExist() {
        when(tripService.getTrip(999L)).thenThrow(new EntityNotFoundException("Trip not found"));

        ResponseEntity<?> mockedResponse = tripController.getTrip(999L);

        assertEquals(HttpStatus.NOT_FOUND, mockedResponse.getStatusCode());
        assertEquals("Trip not found", mockedResponse.getBody());
    }

    @Test
    void shouldDeleteTripSuccessfully() {
        Trip trip = new Trip();
        when(tripService.getTrip(1L)).thenReturn(trip);

        ResponseEntity<?> mockedResponse = tripController.deleteTrip(1L);

        verify(tripService).deleteTrip(1L);
        assertEquals(HttpStatus.NO_CONTENT, mockedResponse.getStatusCode());
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistingTrip() {
        Trip trip = new Trip();
        when(tripService.getTrip(99L)).thenReturn(trip);
        doThrow(new EntityNotFoundException("Trip not found")).when(tripService).deleteTrip(99L);

        ResponseEntity<?> mockedResponse = tripController.deleteTrip(99L);

        assertEquals(HttpStatus.NOT_FOUND, mockedResponse.getStatusCode());
        assertEquals("Trip not found", mockedResponse.getBody());
    }

    @Test
    void shouldCreateTripSuccessfully() {
        TripRequestDto mockedRequest = new TripRequestDto();
        Trip trip = new Trip();

        when(tripService.createTrip(mockedRequest)).thenReturn(trip);

        ResponseEntity<?> mockedResponse = tripController.createTrip(mockedRequest);

        assertEquals(HttpStatus.CREATED, mockedResponse.getStatusCode());
        assertEquals(trip, mockedResponse.getBody());
    }

    @Test
    void shouldReturnNotFoundWhenCreatingTripWithInvalidCity() {
        TripRequestDto mockedRequest = new TripRequestDto();

        when(tripService.createTrip(mockedRequest))
                .thenThrow(new EntityNotFoundException("City not found"));

        ResponseEntity<?> mockedResponse = tripController.createTrip(mockedRequest);

        assertEquals(HttpStatus.NOT_FOUND, mockedResponse.getStatusCode());
        assertEquals("City not found", mockedResponse.getBody());
    }

    @Test
    void shouldUpdateTripSuccessfully() {
        TripRequestDto mockedRequest = new TripRequestDto();
        Trip updatedTrip = new Trip();
        updatedTrip.setTripId(1L);

        when(tripService.updateTrip(1L, mockedRequest)).thenReturn(updatedTrip);

        ResponseEntity<?> mockedResponse = tripController.modifyTrip(1L, mockedRequest);

        assertEquals(HttpStatus.OK, mockedResponse.getStatusCode());
        assertEquals(updatedTrip, mockedResponse.getBody());
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistingTrip() {
        TripRequestDto mockedRequest = new TripRequestDto();

        when(tripService.updateTrip(1L, mockedRequest)).thenThrow(new EntityNotFoundException("Trip not found"));

        ResponseEntity<?> mockedResponse = tripController.modifyTrip(1L, mockedRequest);

        assertEquals(HttpStatus.NOT_FOUND, mockedResponse.getStatusCode());
        assertEquals("Trip not found", mockedResponse.getBody());
    }

}
