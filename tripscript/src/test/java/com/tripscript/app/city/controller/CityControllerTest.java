package com.tripscript.app.city.controller;

import com.tripscript.app.city.model.City;
import com.tripscript.app.city.service.CityService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CityControllerTest {

    @Mock
    private CityService cityService;

    @InjectMocks
    private CityController cityController;

    @Test
    public void shouldReturnAllCitiesWhenFound() {
        List<City> mockedCities = List.of(new City(1L, "Paris", "France", "Beautiful!!"));
        when(cityService.getAllCities()).thenReturn(mockedCities);

        ResponseEntity<List<City>> mockedResponse = cityController.getAllCities();

        assertEquals(HttpStatus.OK, mockedResponse.getStatusCode());
        assertEquals(mockedCities, mockedResponse.getBody());
    }

    @Test
    public void shouldReturnNoContentWhenNoCitiesFound() {
        List<City> mockedCities = new ArrayList<City>();
        when(cityService.getAllCities()).thenReturn(mockedCities);

        ResponseEntity<List<City>> mockedResponse = cityController.getAllCities();

        assertEquals(HttpStatus.NO_CONTENT, mockedResponse.getStatusCode());
        assertNull(mockedResponse.getBody());
    }

    @Test
    public void shouldReturnSingleCityWhenFound() {
        City mockedCity = new City(1L, "Paris", "France", "Beautiful!!");
        when(cityService.getCity(1L)).thenReturn(mockedCity);

        ResponseEntity<?> mockedResponse = cityController.getCity(1L);

        assertEquals(HttpStatus.OK, mockedResponse.getStatusCode());
        assertEquals(mockedCity, mockedResponse.getBody());

    }

    @Test
    public void shouldReturnNotFoundWhenCityMissing() {
        when(cityService.getCity(1L)).thenThrow(new EntityNotFoundException("City not found"));

        ResponseEntity<?> mockedResponse = cityController.getCity(1L);

        assertEquals(HttpStatus.NOT_FOUND, mockedResponse.getStatusCode());
        assertEquals("City not found", mockedResponse.getBody());

    }

    @Test
    public void shouldReturnNoContentWhenCityDeletes() {
        Long cityId = 1L;

        ResponseEntity<?> mockedResponse = cityController.deleteCity(cityId);

        assertEquals(HttpStatus.NO_CONTENT, mockedResponse.getStatusCode());
    }

    @Test
    public void shouldReturnNotFoundWhenDeletingNotExistingCity() {
        Long cityId = 999L;

        doThrow(new EntityNotFoundException("City not found")).when(cityService).deleteCity(cityId);

        ResponseEntity<?> mockedResponse = cityController.deleteCity(cityId);

        assertEquals(HttpStatus.NOT_FOUND, mockedResponse.getStatusCode());
        assertNotNull(mockedResponse.getBody());
        assertTrue(mockedResponse.getBody().toString().contains("City not found"));
    }

    @Test
    void shouldCreateCityWhenValid() {
        City city = new City();
        city.setCityName("Paris");
        city.setCountry("France");

        City savedCity = new City();
        savedCity.setCityId(1L);
        savedCity.setCityName("Paris");
        savedCity.setCountry("France");

        when(cityService.createCity(city)).thenReturn(savedCity);

        ResponseEntity<?> mockedResponse = cityController.createCity(city);

        assertEquals(HttpStatus.CREATED, mockedResponse.getStatusCode());
        assertEquals(savedCity, mockedResponse.getBody());
    }

    @Test
    void shouldReturnBadRequestWhenCityIdProvidedInPost() {
        City city = new City();
        city.setCityId(100L);

        ResponseEntity<?> response = cityController.createCity(city);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("City ID must not be provided. It is auto-generated.", response.getBody());
    }

    @Test
    void shouldUpdateCityWhenValid() {
        Long cityId = 1L;
        City city = new City();
        city.setCityName("Paris");

        City updatedCity = new City();
        updatedCity.setCityId(cityId);
        updatedCity.setCityName("Paris");

        when(cityService.updateCity(cityId, city)).thenReturn(updatedCity);

        ResponseEntity<?> response = cityController.updateCity(cityId, city);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedCity, response.getBody());
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingNonExistingCity() {
        Long cityId = 99L;
        City city = new City();
        city.setCityName("Nowhere");

        when(cityService.updateCity(cityId, city)).thenThrow(new EntityNotFoundException("City not found"));

        ResponseEntity<?> response = cityController.updateCity(cityId, city);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("City not found", response.getBody());
    }
    
}
