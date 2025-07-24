package com.tripscript.app.city.controller;

import com.tripscript.app.city.model.City;
import com.tripscript.app.city.service.CityService;
import com.tripscript.app.constants.Endpoints;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CityController {

    private final CityService cityService;

    @GetMapping(Endpoints.GET_ALL_CITIES)
    public ResponseEntity<List<City>> getAllCities() {
        List<City> cities = cityService.getAllCities();
        if (cities.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cities);
    }

    @GetMapping(Endpoints.GET_CITY)
    public ResponseEntity<?> getCity(@PathVariable Long cityId) {
        try {
            City city = cityService.getCity(cityId);
            return ResponseEntity.ok(city);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping(Endpoints.DELETE_CITY)
    public ResponseEntity<?> deleteCity(@PathVariable Long cityId) {
        try {
            cityService.deleteCity(cityId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping(Endpoints.CREATE_CITY)
    public ResponseEntity<?> createCity(@Valid @RequestBody City city) {
        if(city.getCityId() > 0 ) {
            return ResponseEntity.badRequest().body("City ID must not be provided. It is auto-generated.");
        }
        City newCity = cityService.createCity(city);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCity);
    }

    @PutMapping(Endpoints.MODIFY_CITY)
    public ResponseEntity<City> updateCity(@Valid @PathVariable Long cityId, @RequestBody City city) {
        City existingCity = cityService.updateCity(cityId, city);
        return ResponseEntity.ok(existingCity);

    }
}
