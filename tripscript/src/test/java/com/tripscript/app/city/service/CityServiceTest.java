package com.tripscript.app.city.service;

import com.tripscript.app.city.model.City;
import com.tripscript.app.city.repository.CityRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @InjectMocks
    private CityService cityService;

    @Test
    void shouldReturnAllCities() {
        List<City> mockedCities = List.of(new City(), new City());
        when(cityRepository.findAll()).thenReturn(mockedCities);

        List<City> result = cityService.getAllCities();

        assertEquals(2, result.size());
        verify(cityRepository).findAll();
    }

    @Test
    void shouldReturnCityWhenExists() {
        City city = new City();
        city.setCityId(1L);
        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));

        City result = cityService.getCity(1L);

        assertEquals(1L, result.getCityId());
    }

    @Test
    void shouldThrowExceptionWhenCityNotFound() {
        when(cityRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () ->
                cityService.getCity(1L));

        assertEquals("City with ID 1 not found.", ex.getMessage());
    }

    @Test
    void shouldDeleteCityWhenExists() {
        when(cityRepository.existsById(1L)).thenReturn(true);

        cityService.deleteCity(1L);

        verify(cityRepository).deleteById(1L);
    }

    @Test
    void shouldThrowWhenDeletingNonExistingCity() {
        when(cityRepository.existsById(1L)).thenReturn(false);

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () ->
                cityService.deleteCity(1L));

        assertEquals("City with ID 1 not found.", ex.getMessage());
    }

    @Test
    void shouldCreateCity() {
        City city = new City();
        city.setCityName("Paris");

        when(cityRepository.save(city)).thenReturn(city);

        City result = cityService.createCity(city);

        assertEquals("Paris", result.getCityName());
        verify(cityRepository).save(city);
    }

    @Test
    void shouldUpdateCitySuccessfully() {
        City existingCity = new City();
        existingCity.setCityId(1L);
        existingCity.setCityName("Old");

        City updatedInput = new City();
        updatedInput.setCityName("New");
        updatedInput.setCountry("France");
        updatedInput.setDetails("Updated");

        when(cityRepository.findById(1L)).thenReturn(Optional.of(existingCity));
        when(cityRepository.save(any(City.class))).thenReturn(existingCity);

        City result = cityService.updateCity(1L, updatedInput);

        assertEquals("New", result.getCityName());
        assertEquals("France", result.getCountry());
        assertEquals("Updated", result.getDetails());
    }

    @Test
    void shouldThrowWhenUpdatingNonExistingCity() {
        City city = new City();
        when(cityRepository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () ->
                cityService.updateCity(1L, city));

        assertEquals("City with ID 1 not found.", ex.getMessage());
    }


}
